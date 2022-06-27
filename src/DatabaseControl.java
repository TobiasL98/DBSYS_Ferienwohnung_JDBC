import java.util.ArrayList;
import java.sql.*;

@SuppressWarnings("ALL")
public class DatabaseControl {
    static String name = "dbsys16";
    static String passwd = "dbsys16";

    /**
     * @return List of unique countries in the database
     */
    static ArrayList<String> getCountries() {
        PreparedStatement ps;
        ArrayList<String> countries = new ArrayList<>();
        try {

            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps = conn.prepareStatement("SELECT UNIQUE NAME_LAND FROM DBSYS16.LAND");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countries.add(rs.getString("name_land"));
            }

            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countries;
    }

    static ArrayList<String> getFurnishing() {
        PreparedStatement ps;
        ArrayList<String> furnishing = new ArrayList<>();
        try {

            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps = conn.prepareStatement("SELECT name_ausstattung FROM dbsys16.ausstattungen");
            ResultSet rs = ps.executeQuery();
            furnishing.add("");
            while (rs.next()) {
                furnishing.add(rs.getString("name_ausstattung"));
            }

            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return furnishing;
    }

    static ArrayList<String> noFurnishingQuery(String land, String anreise, String abreise) {
        PreparedStatement ps;
        ArrayList<String> holidayApartment = new ArrayList<>();
        try {
            // JDBC stuff
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps =
                    conn.prepareStatement(
                            """
                                    SELECT b.name_ferienwohnung, avg(b.bewertung)
                                    FROM dbsys16.Ferienwohnung f
                                    LEFT OUTER JOIN dbsys16.Buchung b ON b.name_ferienwohnung = f.name_ferienwohnung
                                    WHERE f.name_land = ?
                                    AND NOT EXISTS (SELECT buchungsnummer FROM dbsys16.Buchung b2
                                                    WHERE b.name_ferienwohnung = b2.name_ferienwohnung
                                                    AND (b2.abreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY')
                                                    OR (b2.anreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY'))
                                                    OR (b2.anreisedatum < TO_DATE(?, 'DD.MM.YYYY') AND b2.abreisedatum > TO_DATE(?, 'DD.MM.YYYY'))))
                                    AND b.name_ferienwohnung IS NOT NULL
                                    GROUP BY b.name_ferienwohnung
                                    ORDER BY avg(b.bewertung) DESC""");
            ps.setString(1, land);
            ps.setString(2, anreise);
            ps.setString(3, abreise);
            ps.setString(4, anreise);
            ps.setString(5, abreise);
            ps.setString(6, anreise);
            ps.setString(7, abreise);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                holidayApartment.add(rs.getString("name_ferienwohnung"));
            }

            // Close connections
            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return holidayApartment;
    }

    public static String getApartmentDetails(String key, String anreise, String abreise) {
        PreparedStatement ps;
        ArrayList<String> details = new ArrayList<>();
        try {
            // JDBC stuff
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps =
                    conn.prepareStatement(
                            """
                                    SELECT b.name_ferienwohnung, avg(b.bewertung) as average, f.preis as preis
                                    FROM dbsys16.Ferienwohnung f
                                    LEFT OUTER JOIN dbsys16.Buchung b ON b.name_ferienwohnung = f.name_ferienwohnung
                                    WHERE f.name_ferienwohnung = ?
                                    AND NOT EXISTS (SELECT buchungsnummer FROM dbsys16.Buchung b2
                                                    WHERE b.name_ferienwohnung = b2.name_ferienwohnung
                                                    AND (b2.abreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY')
                                                    OR (b2.anreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY'))
                                                    OR (b2.anreisedatum < TO_DATE(?, 'DD.MM.YYYY') AND b2.abreisedatum > TO_DATE(?, 'DD.MM.YYYY'))))
                                    AND b.name_ferienwohnung IS NOT NULL
                                    GROUP BY b.name_ferienwohnung, f.preis
                                    ORDER BY avg(b.bewertung) DESC""");


            ps.setString(1, key);
            ps.setString(2, anreise);
            ps.setString(3, abreise);
            ps.setString(4, anreise);
            ps.setString(5, abreise);
            ps.setString(6, anreise);
            ps.setString(7, abreise);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                details.add(
                        "Name: %s Bewertung: %s \u2605 Preis: %s \u20AC"
                                .formatted(
                                        rs.getString("name_ferienwohnung"),
                                        rs.getString("average"),
                                        rs.getString("preis"))
                );
            }

            // Close connections
            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details.get(0);
    }

    public static ArrayList<String> furnishingQuery(String land, String ausstattung, String anreise, String abreise) {
        PreparedStatement ps = null;
        ArrayList<String> holidayApartment = new ArrayList<>();
        try {
            // JDBC stuff
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps = conn.prepareStatement(
                    """
                            SELECT b.name_ferienwohnung, avg(b.bewertung)
                            FROM dbsys16.Ferienwohnung f INNER JOIN dbsys16.IST_AUSGESTATTET_MIT a ON a.name_ferienwohnung = f.name_ferienwohnung
                            LEFT OUTER JOIN dbsys16.Buchung b ON b.name_ferienwohnung = f.name_ferienwohnung
                            WHERE f.name_land = ?
                            AND a.name_ausstattung = ?
                            AND NOT EXISTS (SELECT buchungsnummer FROM dbsys16.Buchung b2
                                            WHERE b.name_ferienwohnung = b2.name_ferienwohnung
                                            AND (b2.abreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY')
                                            OR (b2.anreisedatum BETWEEN TO_DATE(?, 'DD.MM.YYYY') AND TO_DATE(?, 'DD.MM.YYYY'))
                                            OR (b2.anreisedatum < TO_DATE(?, 'DD.MM.YYYY') AND b2.abreisedatum > TO_DATE(?, 'DD.MM.YYYY'))))
                            AND b.name_ferienwohnung IS NOT NULL
                            GROUP BY b.name_ferienwohnung
                            ORDER BY avg(b.bewertung) DESC""");

            ps.setString(1, land);
            ps.setString(2, ausstattung);
            ps.setString(3, anreise);
            ps.setString(4, abreise);
            ps.setString(5, anreise);
            ps.setString(6, abreise);
            ps.setString(7, anreise);
            ps.setString(8, abreise);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                holidayApartment.add(rs.getString("name_ferienwohnung"));
            }

            // Close connections
            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return holidayApartment;
    }


    public static boolean bookApartment(String apartmentName, String anreise, String abreise, String betrag) {
        PreparedStatement ps;
        try {
            // JDBC stuff
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); // Treiber laden
            String url =
                    "jdbc:oracle:thin:@oracle19c.in.htwg-konstanz.de:1521:ora19c"; // String für DB-Connection
            Connection conn = DriverManager.getConnection(url, name, passwd); // Verbindung erstellen

            conn.setTransactionIsolation(
                    Connection.TRANSACTION_SERIALIZABLE); // Transaction Isolations-Level setzen
            conn.setAutoCommit(false); // Kein automatisches Commit

            ps = conn.prepareStatement(
                    """
                            INSERT INTO DBSYS16.BUCHUNG
                            VALUES (
                            null,
                            null,
                            null,
                            SYSDATE,
                             ?,
                             ?,
                             DBMS_RANDOM.RANDOM(),
                             SYSDATE + 7,
                             ?,
                             ?,
                             9)
                            """);

            ps.setString(1, anreise);
            ps.setString(2, abreise);
            ps.setString(3, betrag);
            ps.setString(4, apartmentName);


            ResultSet rs = ps.executeQuery();


            // Close connections
            rs.close(); // Verbindung trennen
            conn.commit();
            conn.close();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

}
