package prosjektGruppe5.Entities;
import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "dat109p1")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;
    @Column(name ="username",unique = true)
    private String userName;
    @Column(name = "fullname",nullable = false)
    private String fullName;
    @Column(name ="paswordhash", nullable = false)
    private String paswordHash;

    @Column(name = "salt")
    private String salt;
    @Column(name = "email", unique = true)
    private String email;

    //-------------------------------------------------------------------------
    // Costructors
    public Person() {}

    public Person(int userId, String userName, String fullName, String paswordHash, String salt ,String email) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.paswordHash = paswordHash;
        this.salt = salt;
        this.email = email;
    }

    //-------------------------------------------------------------------------
    // Relationships
    @OneToMany(mappedBy = "adminId")
    private Set<Game> listOfGames;



    //-------------------------------------------------------------------------
    // Functions

    @Override
    public String toString() {
        return "User{" +
                "userId =" + userId +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", paswordHash='" + paswordHash + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPaswordHash() {
        return paswordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPaswordHash(String paswordHash) {
        this.paswordHash = paswordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
