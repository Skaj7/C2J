/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stamboom.gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import stamboom.controller.StamboomController;
import stamboom.domain.Geslacht;
import stamboom.domain.Gezin;
import stamboom.domain.Persoon;
import stamboom.util.StringUtilities;


/**
 *
 * @author frankpeeters
 */
public class StamboomFXController extends StamboomController implements Initializable {
    
    //MENUs en TABs
    @FXML MenuBar menuBar;
    @FXML MenuItem miNew;
    @FXML MenuItem miOpen;
    @FXML MenuItem miSave;
    @FXML CheckMenuItem cmDatabase;
    @FXML MenuItem miClose;
    @FXML Tab tabPersoon;
    @FXML Tab tabGezin;
    @FXML Tab tabPersoonInvoer;
    @FXML Tab tabGezinInvoer;

    //PERSOON
    @FXML ComboBox cbPersonen;
    @FXML TextField tfPersoonNr;
    @FXML TextField tfVoornamen;
    @FXML TextField tfTussenvoegsel;
    @FXML TextField tfAchternaam;
    @FXML TextField tfGeslacht;
    @FXML TextField tfGebDatum;
    @FXML TextField tfGebPlaats;
    @FXML ComboBox cbOuderlijkGezin;
    @FXML ListView lvAlsOuderBetrokkenBij;
    @FXML Button btStamboom;
    
    //GEZIN
    @FXML ComboBox cbGezin;
    @FXML TextField tfGezinNr;
    @FXML TextField tfGezinOuder1;
    @FXML TextField tfGezinOuder2;
    @FXML TextField tfGezinHuwelijk;
    @FXML TextField tfGezinScheiding;
    @FXML TextField tfGezinKinderen;
    @FXML Button btStamboomGezin;
    
    //INVOER PERSOON
    @FXML TextField tfNieuweVoornamenPersoon;
    @FXML TextField tfNieuweTussenvoegselPersoon;
    @FXML TextField tfNieuweAchternaamPersoon;
    @FXML ComboBox cbGeslachtPersoon;
    @FXML TextField tfNieuweGeboortedatumPersoon;
    @FXML TextField tfNieuweGeboorteplaatsPersoon;
    @FXML ComboBox cbOuderlijkGezinPersoon;
    @FXML Button btOKPersoonInvoer;
    @FXML Button btCancelPersoonInvoer;

    //INVOER GEZIN
    @FXML ComboBox cbOuder1Invoer;
    @FXML ComboBox cbOuder2Invoer;
    @FXML TextField tfHuwelijkInvoer;
    @FXML TextField tfScheidingInvoer;
    @FXML Button btOKGezinInvoer;
    @FXML Button btCancelGezinInvoer;

    //opgave 4
    private boolean withDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboboxes();
        withDatabase = false;
    }

    private void initComboboxes() {
        
        this.cbPersonen.setItems(this.getAdministratie().getPersonen());
        this.cbGezin.setItems(this.getAdministratie().getGezinnen());
        this.cbOuder1Invoer.setItems(this.getAdministratie().getPersonen());
        this.cbOuder2Invoer.setItems(this.getAdministratie().getPersonen());
        this.cbOuderlijkGezin.setItems(this.getAdministratie().getGezinnen());
        this.cbOuderlijkGezinPersoon.setItems(this.getAdministratie().getGezinnen());
        this.cbGeslachtPersoon.getItems().clear();
        this.cbGeslachtPersoon.getItems().addAll(Geslacht.MAN, Geslacht.VROUW);
    }

    public void selectPersoon(Event evt) {
        Persoon persoon = (Persoon) cbPersonen.getSelectionModel().getSelectedItem();
        showPersoon(persoon);
    }

    private void showPersoon(Persoon persoon) {
        if (persoon == null) {
            clearTabPersoon();
        } else {
            tfPersoonNr.setText(persoon.getNr() + "");
            tfVoornamen.setText(persoon.getVoornamen());
            tfTussenvoegsel.setText(persoon.getTussenvoegsel());
            tfAchternaam.setText(persoon.getAchternaam());
            tfGeslacht.setText(persoon.getGeslacht().toString());
            tfGebDatum.setText(StringUtilities.datumString(persoon.getGebDat()));
            tfGebPlaats.setText(persoon.getGebPlaats());
            if (persoon.getOuderlijkGezin() != null) {
                cbOuderlijkGezin.getSelectionModel().select(persoon.getOuderlijkGezin());
            } else {
                cbOuderlijkGezin.getSelectionModel().clearSelection();
            }

            //todo opgave 3
            //lvAlsOuderBetrokkenBij.setItems(persoon.getAlsOuderBetrokkenIn());
        }
    }

    public void setOuders(Event evt) {
        if (tfPersoonNr.getText().isEmpty()) {
            return;
        }
        Gezin ouderlijkGezin = (Gezin) cbOuderlijkGezin.getSelectionModel().getSelectedItem();
        if (ouderlijkGezin == null) {
            return;
        }

        int nr = Integer.parseInt(tfPersoonNr.getText());
        Persoon p = getAdministratie().getPersoon(nr);
        if(getAdministratie().setOuders(p, ouderlijkGezin)){
            showDialog("Success", ouderlijkGezin.toString()
                + " is nu het ouderlijk gezin van " + p.getNaam());
        }
        
    }
    
    public void selectGezin(Event evt) {
        // todo opgave 3
        Gezin gezin = (Gezin) cbGezin.getSelectionModel().getSelectedItem();
        showGezin(gezin);

    }

    private void showGezin(Gezin gezin) {
        // todo opgave 3

    }

    public void setHuwdatum(Event evt) {
        // todo opgave 3
        Persoon o1 = null;
        Persoon o2 = null;
        for(Persoon p : getAdministratie().getPersonen())
        {
         if(p.getNaam() == tfGezinOuder1.getText())
         {
             o1 = p;
         }
         if(p.getNaam() == tfGezinOuder2.getText())
         {
             o2 = p;
         }
        }
        getAdministratie().addHuwelijk(o1, o2,StringUtilities.datum(tfHuwelijkInvoer.getText()));
        
    }

    public void setScheidingsdatum(Event evt) {
        // todo opgave 3
        for(Gezin g1 : getAdministratie().getGezinnen())
        {
            if(g1.getOuder1().getNaam() == tfGezinOuder1.getText() || g1.getOuder1().getNaam() == tfGezinOuder2.getText())
            {
                if(g1.getOuder2().getNaam()== tfGezinOuder1.getText() || g1.getOuder2().getNaam() == tfGezinOuder2.getText())
                {
                    getAdministratie().setScheiding(g1,StringUtilities.datum(tfHuwelijkInvoer.getText()));
                }
            }
        }

    }

    public void cancelPersoonInvoer(Event evt) {
        clearTabPersoonInvoer();
    }

    public void okPersoonInvoer(Event evt) {
        // todo opgave 3
        

        String[] vnaam = tfNieuweVoornamenPersoon.getText().split(" ");

        Calendar gebdat = StringUtilities.datum(tfNieuweGeboortedatumPersoon.getText());

        getAdministratie().addPersoon((Geslacht)cbGeslachtPersoon.getSelectionModel().getSelectedItem(), vnaam, tfNieuweAchternaamPersoon.getText(), tfNieuweTussenvoegselPersoon.getText(),gebdat, tfNieuweGeboorteplaatsPersoon.getText(), (Gezin)cbOuderlijkGezinPersoon.getSelectionModel().getSelectedItem());
        
        clearTabPersoonInvoer();
    }

    public void okGezinInvoer(Event evt) {
        Persoon ouder1 = (Persoon) cbOuder1Invoer.getSelectionModel().getSelectedItem();
        if (ouder1 == null) {
            showDialog("Warning", "eerste ouder is niet ingevoerd");
            return;
        }
        Persoon ouder2 = (Persoon) cbOuder2Invoer.getSelectionModel().getSelectedItem();
        Calendar huwdatum;
        try {
            huwdatum = StringUtilities.datum(tfHuwelijkInvoer.getText());
        } catch (IllegalArgumentException exc) {
            showDialog("Warning", "huwelijksdatum :" + exc.getMessage());
            return;
        }
        Gezin g;
        if (huwdatum != null) {
            g = getAdministratie().addHuwelijk(ouder1, ouder2, huwdatum);
            if (g == null) {
                showDialog("Warning", "Invoer huwelijk is niet geaccepteerd");
            } else {
                Calendar scheidingsdatum;
                try {
                    scheidingsdatum = StringUtilities.datum(tfScheidingInvoer.getText());
                    if(scheidingsdatum != null){
                        getAdministratie().setScheiding(g, scheidingsdatum);
                    }
                } catch (IllegalArgumentException exc) {
                    showDialog("Warning", "scheidingsdatum :" + exc.getMessage());
                }
            }
        } else {
            g = getAdministratie().addOngehuwdGezin(ouder1, ouder2);
            if (g == null) {
                showDialog("Warning", "Invoer ongehuwd gezin is niet geaccepteerd");
            }
        }

        clearTabGezinInvoer();
    }
    

    public void cancelGezinInvoer(Event evt) {
        clearTabGezinInvoer();
    }

    
    public void showStamboom(Event evt) {
        // todo opgave 3
        
    }

    public void createEmptyStamboom(Event evt) {
        this.clearAdministratie();
        clearTabs();
        initComboboxes();
    }

    
    public void openStamboom(Event evt) {
        // todo opgave 3
       
    }

    
    public void saveStamboom(Event evt) {
        // todo opgave 3
       
    }

    
    public void closeApplication(Event evt) {
        saveStamboom(evt);
        getStage().close();
    }

   
    public void configureStorage(Event evt) {
        withDatabase = cmDatabase.isSelected();
    }

 
    public void selectTab(Event evt) {
        Object source = evt.getSource();
        if (source == tabPersoon) {
            clearTabPersoon();
        } else if (source == tabGezin) {
            clearTabGezin();
        } else if (source == tabPersoonInvoer) {
            clearTabPersoonInvoer();
        } else if (source == tabGezinInvoer) {
            clearTabGezinInvoer();
        }
    }

    private void clearTabs() {
        clearTabPersoon();
        clearTabPersoonInvoer();
        clearTabGezin();
        clearTabGezinInvoer();
    }

    
    private void clearTabPersoonInvoer() {
        tfNieuweVoornamenPersoon.clear();
        tfNieuweTussenvoegselPersoon.clear();
        tfNieuweAchternaamPersoon.clear();
        cbGeslachtPersoon.getSelectionModel().clearSelection();
        tfNieuweGeboortedatumPersoon.clear();
        tfNieuweGeboorteplaatsPersoon.clear();
        cbOuderlijkGezinPersoon.getSelectionModel().clearSelection();
    }

    
    private void clearTabGezinInvoer() {
        cbOuder1Invoer.getSelectionModel().clearSelection();
        cbOuder2Invoer.getSelectionModel().clearSelection();
        tfHuwelijkInvoer.clear();
        tfScheidingInvoer.clear();
    
    }

    private void clearTabPersoon() {
        cbPersonen.getSelectionModel().clearSelection();
        tfPersoonNr.clear();
        tfVoornamen.clear();
        tfTussenvoegsel.clear();
        tfAchternaam.clear();
        tfGeslacht.clear();
        tfGebDatum.clear();
        tfGebPlaats.clear();
        cbOuderlijkGezin.getSelectionModel().clearSelection();
        lvAlsOuderBetrokkenBij.setItems(FXCollections.emptyObservableList());
    }

    
    private void clearTabGezin() {
        cbGezin.getSelectionModel().clearSelection();
        tfGezinNr.clear();
        tfGezinOuder1.clear();
        tfGezinOuder2.clear();
        tfGezinHuwelijk.clear();
        tfGezinScheiding.clear();
        tfGezinKinderen.clear();
    }

    private void showDialog(String type, String message) {
        Stage myDialog = new Dialog(getStage(), type, message);
        myDialog.show();
    }

    private Stage getStage() {
        return (Stage) menuBar.getScene().getWindow();
    }

}
