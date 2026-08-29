package com.cbag.autoatendimento.service;

import com.cbag.autoatendimento.config.StaticConfigObjects;
import com.cbag.autoatendimento.exception.ErroAoDefinirASenhaException;
import com.cbag.autoatendimento.exception.SenhaIndefinidaException;
import com.cbag.autoatendimento.exception.SenhaJaDefinidaException;
import com.cbag.autoatendimento.exception.SenhaMuitoLongaException;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SenhaService {
    private static final int maxSenhaSize = 10;

    private static String senha;
    static{
        try{
            File senhaF = createDirAndReturnFile();
            if(!senhaF.exists()||!senhaF.isFile()){ //se não existir ou não for um arquivo
                senha = null;
            }else{
                FileReader fileReader = new FileReader(senhaF);
                char[] buffer = new char[maxSenhaSize];
                fileReader.read(buffer);
                senha = String.copyValueOf(buffer);
                fileReader.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static File createDirAndReturnFile(){
        File senhaF = new File(StaticConfigObjects.userHomeDir+"/Autoatendimento");
        if(!senhaF.exists()){
            senhaF.mkdir();
        }
        return new File(StaticConfigObjects.userHomeDir+"/Autoatendimento/senha.txt");
    }

    public static String getSenha(){
        if(senha == null){
            throw new SenhaIndefinidaException();
        }else{
            return senha;
        }
    }

    public static void setSenha(String senhaNova){
        if(senha != null){
            throw new SenhaJaDefinidaException();
        }
        if(senhaNova.length()>maxSenhaSize){
            throw new SenhaMuitoLongaException();
        }
        senha = senhaNova;
        File senhaF = createDirAndReturnFile();
        try{
            FileWriter fw = new FileWriter(senhaF);
            fw.write(senhaNova);
            fw.close();
        } catch (Exception e) {
            throw new ErroAoDefinirASenhaException(e.getMessage());
        }
    }

    public static boolean correta(String typedSenha){
        if(senha == null){
            throw new SenhaIndefinidaException();
        }
        return typedSenha.equals(senha);
    }

}
