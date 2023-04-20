package prosjektGruppe5.Utilities;

/* Here you can manually choose who is admin and therefore will be allowed-
* to view the list of people in the database and has the ability to delete them.
* An admin CANNOT delete himself
*/
public class myAdminClass {
    private String adminUsername ="Admin123";

    public myAdminClass() {
        adminUsername = "Admin123"; // set the fixed value in the constructor
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }


}
