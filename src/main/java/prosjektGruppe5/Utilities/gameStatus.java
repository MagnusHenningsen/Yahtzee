package prosjektGruppe5.Utilities;

/** Enum representing all the status the Yahtze game can have.
 */
public enum gameStatus {
    VENTER_PÅ_SPILLERE("Venter på spillere"),
    SPILL_STARTET("Spill startet"),
    FINISHED("Ferdig"),
    PAUSE("Pause");

    private final String statusDescription;

    //-------------------------------------------------------------
    //Constructors
    gameStatus (String statusDescription) {
        this.statusDescription = statusDescription;
    }

    //------------------------------------------------------------
    //Functions
    public String getStatusDescription() {
        return statusDescription;
    }
}
