/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author 01494237210
 */
public class Usuario {
    private int usucod;
    private String usunome;
    private String ususenha;
    private String usuemail;
    private String fotoCaminho;

    public String getFotocaminho() {
        return fotoCaminho;
    }

    public void setFotocaminho(String fotocaminho) {
        this.fotoCaminho = fotocaminho;
    }
    

    public int getUsucod() {
        return usucod;
    }

    public void setUsucod(int usucod) {
        this.usucod = usucod;
    }

    public String getUsunome() {
        return usunome;
    }

    public void setUsunome(String usunome) {
        this.usunome = usunome;
    }

    public String getUsusenha() {
        return ususenha;
    }

    public void setUsusenha(String ususenha) {
        this.ususenha = ususenha;
    }

    public String getUsuemail() {
        return usuemail;
    }

    public void setUsuemail(String usuemail) {
        this.usuemail = usuemail;
    }
    
    
}
