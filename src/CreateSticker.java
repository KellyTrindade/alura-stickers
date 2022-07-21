import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.awt.Font;
import java.awt.Graphics2D;

public class CreateSticker {
    private int m_width;
    private int m_height;
    private int m_stickerHeight;

    public void create( InputStream inputStream, String fileName ) throws Exception {
        // buscar imagem
        BufferedImage originalImage = ImageIO.read( inputStream );
        
        // criar nova imagem em memória com transparência
        m_width = originalImage.getWidth();
        m_height = originalImage.getHeight();
        m_stickerHeight = m_height + 200;

        BufferedImage sticker = new BufferedImage( m_width, m_stickerHeight, BufferedImage.TRANSLUCENT );

        // copiar a imagem original para a nova imagem
        Graphics2D graphics = (Graphics2D) sticker.getGraphics();
        graphics.drawImage( originalImage, null, 0, 0 );
        
        // adicionar texto na nova imagem
        graphics.setFont( new Font( Font.SANS_SERIF, Font.BOLD, 56 ) );
        graphics.drawString( "top", 0, ( m_stickerHeight - 100 ) );

        // gerar arquivo da nova imagem
        ImageIO.write( sticker, "png", new File( String.format("../output/%s", fileName ) ) );
    }
}
