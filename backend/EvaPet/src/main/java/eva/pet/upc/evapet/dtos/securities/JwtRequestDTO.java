package eva.pet.upc.evapet.dtos.securities;

public class JwtRequestDTO  {
    private String mail;
    private String password;
    public JwtRequestDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    public JwtRequestDTO(String mail, String password) {
        super();
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }
    public String getPassword() {
        return password;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}