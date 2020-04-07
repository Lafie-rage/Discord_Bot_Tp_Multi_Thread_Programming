/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BotDidiDUT;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 *
 * @author Corentin
 */
public class BotDidiDUT {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String TOKEN_BOT = args[0];
        JDA api = new JDABuilder(TOKEN_BOT).build();
        api.addEventListener(new MyListener());
    }
    
}
