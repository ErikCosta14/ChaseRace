package corridadecarrosvirtual;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioUtil {
    public static void tocarSom(String caminhoDoArquivo) throws Exception{
        //carrega o arquivo de audio do caminho selecionado
        try {
            File arquivo = new File(caminhoDoArquivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); //faz o áudio ficar em loop
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20.0f); //controla o volume do áudio
            clip.start(); // Inicia o som
        } catch (Exception e) {
            throw new Exception("Áudio não suportado"); //caso ocorra um erro é gerado um erro para ser mostrado na tela posteriormente
        }
    }
}
