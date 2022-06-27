import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author philipp
 */
public class GUI extends javax.swing.JFrame {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DefaultListModel<String> model = new DefaultListModel<>();

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        // To check for the correct date format

        DateFormatter dateFormatter = new DateFormatter(dateFormat);

        // Init components
        jPanel1 = new javax.swing.JPanel();
        landComboBox =
                new javax.swing.JComboBox<>(DatabaseControl.getCountries().toArray(new String[0]));
        jLabel1 = new javax.swing.JLabel();
        ausstattungComboBox =
                new javax.swing.JComboBox<>(DatabaseControl.getFurnishing().toArray(new String[0]));
        jLabel2 = new javax.swing.JLabel();
        anreiseDatum = new javax.swing.JFormattedTextField(dateFormatter);
        jLabel3 = new javax.swing.JLabel();
        abreiseDatum = new javax.swing.JFormattedTextField(dateFormatter);
        jLabel4 = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        holidayApartmentSelection = new javax.swing.JList<>(model);
        bookButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Action Listeners
        // jComboBox1.addActionListener();

        jLabel1.setText("Auswahl des Landes");

        jLabel2.setText("Ausstattungen");

        jLabel3.setText("Anreisedatum");

        jLabel4.setText("Abreisedatum");

        searchButton.setText("Suchen");
        searchButton.setActionCommand("Suchen");
        searchButton.addActionListener(this::searchButton);
        bookButton.addActionListener(this::bookButton);

        holidayApartmentSelection.setModel(
                new javax.swing.AbstractListModel<>() {
                    String[] strings = {};

                    public int getSize() {
                        return strings.length;
                    }

                    public String getElementAt(int i) {
                        return strings[i];
                    }
                });
        jScrollPane1.setViewportView(holidayApartmentSelection);

        bookButton.setText("Buchen");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel1)
                                                                                        .addComponent(
                                                                                                landComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                184,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(
                                                                                                ausstattungComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                170,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel2))
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(
                                                                                                anreiseDatum,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                184,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel3)))
                                                        .addComponent(jScrollPane1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(
                                                                                                abreiseDatum,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                170,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(
                                                                                                jPanel1Layout
                                                                                                        .createSequentialGroup()
                                                                                                        .addGap(14, 14, 14)
                                                                                                        .addGroup(
                                                                                                                jPanel1Layout
                                                                                                                        .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment
                                                                                                                                        .LEADING)
                                                                                                                        .addComponent(bookButton)
                                                                                                                        .addComponent(searchButton))))
                                                                        .addContainerGap(
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(jLabel4)
                                                                        .addGap(0, 0, Short.MAX_VALUE)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(
                                                                landComboBox,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(
                                                                ausstattungComboBox,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(
                                                                anreiseDatum,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(
                                                                abreiseDatum,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                162,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addGap(15, 15, 15)
                                                                        .addComponent(searchButton)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(bookButton)))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(
                                jPanel1,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addComponent(
                                                jPanel1,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 27, Short.MAX_VALUE)));

        pack();
    } // </editor-fold>

    private void searchButton(java.awt.event.ActionEvent evt) {
        searchQuery();
    }

    private void bookButton(java.awt.event.ActionEvent evt) {
        bookAction();
    }

    /**
     * This method does all the logic for the search button
     */
    private void searchQuery() {
        try {
            Date anreise = dateFormat.parse(anreiseDatum.getText());
            Date abreise = dateFormat.parse(abreiseDatum.getText());
            long timeDifference =
                    TimeUnit.DAYS.convert(
                            Math.abs(abreise.getTime() - anreise.getTime()), TimeUnit.MILLISECONDS);
            if (timeDifference < 3) {
                JOptionPane.showMessageDialog(this, "Die Mindestbuchungszeit beträgt 3 Tage");
                return;
            }
            if (Objects.requireNonNull(ausstattungComboBox.getSelectedItem()).equals("")) {
                try {
                    List<String> holidayApartment =
                            DatabaseControl.noFurnishingQuery(
                                    String.valueOf(landComboBox.getSelectedItem()),
                                    anreiseDatum.getText(),
                                    abreiseDatum.getText());
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for (String s : holidayApartment) {
                        model.addElement(
                                DatabaseControl.getApartmentDetails(
                                        s,
                                        anreiseDatum.getText(),
                                        abreiseDatum.getText()
                                )
                        );
                    }
                    holidayApartmentSelection.setModel(model);
                    this.repaint();
                    if (holidayApartment.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                this, "Es konnte keine Wohnung gefunden werden");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    List<String> holidayApartments =
                            DatabaseControl.furnishingQuery(
                                    String.valueOf(landComboBox.getSelectedItem()),
                                    String.valueOf(ausstattungComboBox.getSelectedItem()),
                                    anreiseDatum.getText(),
                                    abreiseDatum.getText()
                            );
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for (String s : holidayApartments) {
                        model.addElement(
                                DatabaseControl.getApartmentDetails(
                                        s,
                                        anreiseDatum.getText(),
                                        abreiseDatum.getText()
                                )
                        );
                    }
                    holidayApartmentSelection.setModel(model);
                    this.repaint();

                    if (holidayApartments.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                this, "Es konnte keine Wohnung gefunden werden");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method does all the logic for the book apartment action
     */
    private void bookAction() {
        if (holidayApartmentSelection.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Bitte wählen sie die zu buchende Wohnung aus!");
            return;
        }
        String apartmentName =
                holidayApartmentSelection
                        .getSelectedValue()
                        .split(" ")[1];

        String anreise = anreiseDatum.getText();
        String abreise = abreiseDatum.getText();
        String betrag = holidayApartmentSelection
                .getSelectedValue()
                .split(" ")[6];
        if (!DatabaseControl.bookApartment(apartmentName, anreise, abreise, betrag)) {
            JOptionPane.showMessageDialog(
                    this, "Bitte wählen sie die zu buchende Wohnung aus!");
        } else {
            holidayApartmentSelection.setModel(new AbstractListModel<String>() {
                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public String getElementAt(int index) {
                    return null;
                }
            });
            this.repaint();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(
                () -> new GUI().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton searchButton;
    private javax.swing.JButton bookButton;
    private javax.swing.JComboBox<String> landComboBox;
    private javax.swing.JComboBox<String> ausstattungComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> holidayApartmentSelection;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField anreiseDatum;
    private javax.swing.JFormattedTextField abreiseDatum;
    // End of variables declaration
}
