import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import static java.lang.System.exit;

public class Teil7 {

    static volatile Boolean runBit = false;
    static volatile Boolean buchenBit = false;

    public static void main(String[] args) throws SQLException {

        int anzahlLänder = 0;
        int anzahlAusstattungen = 0;

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String url = "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c";
        Connection conn = DriverManager.getConnection(url, "dbsys23", "dbsys23");
        if (conn == null)
            System.out.println("KEINE CONNECTION!");

        // Anzahl Länder ermitteln
        PreparedStatement ps = conn.prepareStatement("SELECT count(name_land) FROM dbsys23.land");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            anzahlLänder = rs.getInt("count(name_land)");
        }
        // Array mit ländern erzeugen und füllen
        String[] länder = new String[anzahlLänder];
        ps = conn.prepareStatement("SELECT name_land FROM dbsys23.land");
        rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            länder[i] = rs.getString("name_land");
            i++;
        }

        // Anzahl ausstattungen ermitteln
        ps = conn.prepareStatement("SELECT count(name_ausstattung) FROM dbsys23.ausstattungen");
        rs = ps.executeQuery();
        while(rs.next()) {
            anzahlAusstattungen = rs.getInt("count(name_ausstattung)");
        }
        // Array mit ausstattungen erzeugen und füllen
        String[] ausstattungen = new String[anzahlAusstattungen + 1];
        ps = conn.prepareStatement("SELECT name_ausstattung FROM dbsys23.ausstattungen");
        rs = ps.executeQuery();
        i = 0;
        ausstattungen[i] = "-----";
        i++;
        while (rs.next()) {
            ausstattungen[i] = rs.getString("name_ausstattung");
            i++;
        }


        // Mainframe
        JFrame mainFrame = new JFrame("Ferienwohnung Buchung");
        mainFrame.setSize(800,200);

        // Dropown Länder
        JComboBox<String> dropDownLänder = new JComboBox<>(länder);
        dropDownLänder.setBounds(80, 50, 140, 20);

        // Dropdown Ausstattungen
        JComboBox<String> dropDownAusstattung = new JComboBox<>(ausstattungen);
        dropDownLänder.setBounds(80, 50, 140, 20);

        // Anreise/Abreisedatum
        JTextField anreiseDatum = new JTextField("", 15);
        JTextField abreiseDatum = new JTextField("", 15);

        // Confirm Button
        JCheckBox suchen = new JCheckBox("Suchen");
        suchen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    runBit = true;

                } else {//checkbox has been deselected
                    runBit = false;
                };
            }
        });

        // mainpanel
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new FlowLayout());
        mainpanel.add(dropDownLänder);
        mainpanel.add(dropDownAusstattung);
        mainpanel.add(new JLabel("Anreise Datum:"));
        mainpanel.add(anreiseDatum);
        mainpanel.add(new JLabel("Abreise Datum:"));
        mainpanel.add(abreiseDatum);
        mainpanel.add(suchen);

        // Add panel to mainframe
        mainFrame.add(mainpanel);
        mainFrame.setVisible(true);

        // Auswählen
        while (!runBit) Thread.onSpinWait();

        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));

        String land = dropDownLänder.getItemAt(dropDownLänder.getSelectedIndex());
        String ausstattung = dropDownAusstattung.getItemAt(dropDownAusstattung.getSelectedIndex());

        String anreise = anreiseDatum.getText();
        String abreise = abreiseDatum.getText();

        if (ausstattung.equals("-----")) {
            ps = conn.prepareStatement(
                    "SELECT b.name_ferienwohnung, avg(b.bewertung)\n" +
                            "FROM dbsys23.Ferienwohnung f\n" +
                            "LEFT OUTER JOIN dbsys23.Buchung b ON b.name_ferienwohnung = f.name_ferienwohnung\n" +
                            "WHERE f.name_land = ?\n" +
                            "AND NOT EXISTS (SELECT buchungsnummer FROM dbsys23.Buchung b2\n" +
                            "                WHERE b.name_ferienwohnung = b2.name_ferienwohnung\n" +
                            "                AND (b2.abreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY')\n" +
                            "                OR (b2.anreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY'))\n" +
                            "                OR (b2.anreisedatum < TO_DATE(?, 'DD.MM.YYYY') AND b2.abreisedatum > TO_DATE(?, 'DD.MM.YYYY'))))\n" +
                            "AND b.name_ferienwohnung IS NOT NULL\n" +
                            "GROUP BY b.name_ferienwohnung\n" +
                            "ORDER BY avg(b.bewertung) DESC");
            ps.setString(1, land);
            ps.setString(2, anreise);
            ps.setString(3, abreise);
            ps.setString(4, anreise);
            ps.setString(5, abreise);
            ps.setString(6, anreise);
            ps.setString(7, abreise);
            rs = ps.executeQuery();
        } else {
            ps = conn.prepareStatement(
                    "SELECT b.name_ferienwohnung, avg(b.bewertung)\n" +
                            "FROM dbsys23.Ferienwohnung f INNER JOIN dbsys23.besitzt a ON a.name_ferienwohnung = f.name_ferienwohnung\n" +
                            "LEFT OUTER JOIN dbsys23.Buchung b ON b.name_ferienwohnung = f.name_ferienwohnung\n" +
                            "WHERE f.name_land = ?\n" +
                            "AND a.name_ausstattung = ?\n" +
                            "AND NOT EXISTS (SELECT buchungsnummer FROM dbsys23.Buchung b2\n" +
                            "                WHERE b.name_ferienwohnung = b2.name_ferienwohnung\n" +
                            "                AND (b2.abreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY')\n" +
                            "                OR (b2.anreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY'))\n" +
                            "                OR (b2.anreisedatum < TO_DATE(?, 'DD.MM.YYYY') AND b2.abreisedatum > TO_DATE(?, 'DD.MM.YYYY'))))\n" +
                            "AND b.name_ferienwohnung IS NOT NULL\n" +
                            "GROUP BY b.name_ferienwohnung\n" +
                            "ORDER BY avg(b.bewertung) DESC");
            ps.setString(1, land);
            ps.setString(2, ausstattung);
            ps.setString(3, anreise);
            ps.setString(4, abreise);
            ps.setString(5, anreise);
            ps.setString(6, abreise);
            ps.setString(7, anreise);
            ps.setString(8, abreise);
            rs = ps.executeQuery();
        }

        String[] trefferName = new String[100];
        int[] trefferBewertung = new int[100];

        int index = 0;
        while (rs.next()) {
            String r1 = rs.getString("name_ferienwohnung");
            try {
                trefferName[index] = r1;
            } catch (Exception e){
                System.out.println("Mehr als 100 treffer!");
                break;
            }
            int r2 = rs.getInt("avg(b.bewertung)");
            trefferBewertung[index] = r2;
            index++;
        }

        int[] preise = new int[100];
        PreparedStatement getPreis = conn.prepareStatement("SELECT Preis from dbsys23.Ferienwohnung WHERE name_ferienwohnung = ?");
        ResultSet preisRes;
        for (int j = 0; j < index; j++) {
            getPreis.setString(1, trefferName[j]);
            preisRes = getPreis.executeQuery();
            while (preisRes.next()) {
                preise[j] = preisRes.getInt("PREIS");
                System.out.println(preise[j]);
            }
            preisRes.close();
        }
        getPreis.close();

        String[] combined = new String[index];

        for (int j = 0; j < index; j++) {
            combined[j] = trefferName[j] + "    Bewertung: " +  Integer.toString(trefferBewertung[j]) + "    Preis pro Woche: " + preise[j];
        }

        // 2. Fenster für Ausgabe
        JFrame ausgabeFrame = new JFrame("Beispiel JFrame");
        ausgabeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ausgabeFrame.setSize(800,200);

        // Treffer ausgeben
        JComboBox<String> ausgabe = new JComboBox<>(combined);

        // Email eingeben
        JTextField email = new JTextField(30);

        // Confirm Button
        JButton buchenButton = new JButton("Buchen");
        buchenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (buchenButton.isEnabled()) {
                    buchenBit = true;
                }
                if (!buchenButton.isEnabled()) {
                    buchenBit = false;
                }
            }
        });

        // panel für ausgabe
        JPanel panelAusgabe = new JPanel();
        panelAusgabe.setLayout(new FlowLayout());
        panelAusgabe.add(ausgabe);
        panelAusgabe.add(new JLabel("E-Mail: "));
        panelAusgabe.add(email);
        panelAusgabe.add(buchenButton);

        // Ausgabe
        ausgabeFrame.add(panelAusgabe);
        ausgabeFrame.setVisible(true);

        while (!buchenBit) Thread.onSpinWait();

        String mailAdresse = email.getText();
        String auswahlFerienwohnung = trefferName[ausgabe.getSelectedIndex()];
        int preis = preise[ausgabe.getSelectedIndex()];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bewertungsDatumTime = LocalDateTime.now().plusDays(10);

        String sysDate = dtf.format(now);
        String bewertungsDatum = dtf.format(bewertungsDatumTime);

        PreparedStatement update = conn.prepareStatement("INSERT INTO dbsys23.buchung(buchungsnummer, bewertung, bewertungsdatum, buchungsdatum, anreisedatum, abreisedatum, rechnungsnummer, rechnungsdatum, betrag, name_ferienwohnung, kundennummer)\n" +
                "        VALUES(dbsys23.buchungsNR.NextVal, ?, TO_DATE(?, 'DD.MM.YYYY'), TO_DATE(?, 'DD.MM.YYYY'), TO_DATE(?, 'DD.MM.YYYY'), TO_DATE(?, 'DD.MM.YYYY'), ?, TO_DATE(?, 'DD.MM.YYYY'), ?, ?, ?)");
        update.setInt(1, 5);
        update.setString(2, bewertungsDatum);
        update.setString(3, sysDate);
        update.setString(4, anreise);
        update.setString(5, abreise);
        update.setInt(6, 100);
        update.setString(7, sysDate);
        update.setInt(8, preis);
        update.setString(9, auswahlFerienwohnung);
        update.setInt(10, 1);
        update.executeUpdate();

        try {
            update.close(); conn.setAutoCommit(false); conn.commit(); conn.close(); rs.close(); ps.close();
        } catch (SQLException se) {
            System.out.println();
            System.out.println("SQL Exception occurred while establishing connection to DBS: " + se.getMessage());
            System.out.println("- SQL state  : " + se.getSQLState());
            System.out.println("- Message    : " + se.getMessage());
            System.out.println("- Vendor code: " + se.getErrorCode());
            System.out.println();
            System.out.println("EXITING WITH FAILURE ... !!!");
            System.out.println();
            try {
                conn.rollback();														// Rollback durchführen
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(-1);
        }
        ausgabeFrame.dispatchEvent(new WindowEvent(ausgabeFrame, WindowEvent.WINDOW_CLOSING));

        exit(1);
    }
}