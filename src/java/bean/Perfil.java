/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import java.util.List;

/**
 *
 * @author 01494237210
 */
public class Perfil {
    private int id;
    private String nome;
    private String bio;
    private String fotocaminho;
    private List<Url> urls;

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFotocaminho() {
        return fotocaminho;
    }

    public void setFotocaminho(String fotocaminho) {
        this.fotocaminho = fotocaminho;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
    
    
    
}
