
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Castle
 */
public class Pencere extends JPanel implements ActionListener{
    
    
    private boolean  engelekleme=false;
    
    private int aralik = 40;
    private ImageIcon icon = new ImageIcon("poketop.png");
    private ImageIcon icon2 = new ImageIcon("ftop.png");
    private BasicStroke ayarla = new BasicStroke(5);
    private BorderLayout borderlay = new BorderLayout(5,5); 
    private JButton basla = new JButton("Basla");
    private JButton engel_ekle = new JButton("Engel_Ekle");
    private JButton oge_yerlestir = new JButton("Oge_Ekle");
    private JButton botlu = new JButton("Kullanici_Sec");
    private JComponent islem_menusu;
    private boolean buton_aktif=false;
    private Stack<Point> Kara_Kutu = new Stack<>();
    private int [][] Kara_Kutu_Dizi;
    private int delay = 5;
    private boolean bot_durum=false;
    private Timer  animasyon = new Timer(delay,this);
    
    
    private Point basla1 = new Point(65, 140);
    private Point basla2 = new Point(65, 140);
   
    /////////////
    private Point bitis1 = new Point(65, 250);
    private Point bitis2 = new Point(90, 210);
    
    private Stack<Point> Gecis_Noktalari1 = new Stack<>();
    private Stack<Point> Gecis_Noktalari2 = new Stack<>();
    
    int ikinci_x=bitis2.x,ikinci_y=bitis2.y;
    
    
   
    int sayac=0;
    
    public Pencere()
    {
        basla.setName("Basla");
        engel_ekle.setName("Engel_Ekle");
        oge_yerlestir.setName("Oge_Ekle");
        botlu.setName("Kullanici");
        ////////
        
       
        islem_menusu = MenuOlustur();
        this.setLayout(borderlay);
        add(islem_menusu,BorderLayout.EAST);
        basla.addMouseListener(new Dinleyici());
        engel_ekle.addMouseListener(new Dinleyici());
        oge_yerlestir.addMouseListener(new Dinleyici());
        botlu.addMouseListener(new Dinleyici());
        addKeyListener(new Klavye_Isler());
        
        
    }
    
    
     private  JComponent MenuOlustur() 
     {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(5, 1, 5, 25));
        inner.add(oge_yerlestir);
        inner.add(basla);
        inner.add(engel_ekle);
        inner.add(botlu);
       
        
        return inner;
    }
    void Yaris_Baslat()
    {
        Kara_Kutu_Dizi = new int[Kara_Kutu.size()][2];
        
         for(int i=0;i<Kara_Kutu.size();i++)
        {
            Kara_Kutu_Dizi[i][0] = Kara_Kutu.get(i).x;
            Kara_Kutu_Dizi[i][1] = Kara_Kutu.get(i).y;
        }
        Araba araba1 = new Araba(600, 600, basla1, bitis1, 15, 10, Kara_Kutu_Dizi);
        Araba araba2 = new Araba(600, 600, basla2, bitis2, 15, 10, Kara_Kutu_Dizi);
        Gecis_Noktalari1=araba1.test();
        Gecis_Noktalari2=araba2.test();
        
    }
    private void NoktaEkleme()
    {
        Kara_Kutu=Line_To_Dizi(0, 0, 500 +2* aralik, 0, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(0, 250 + 2*aralik, 500 +2* aralik, 250 + 2*aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(0, 0, 0, 250 + 2*aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(500 +2* aralik, 0, 500 +2* aralik, 250 + 2*aralik, Kara_Kutu);
         

        //Siyahlar
        
         //////////////////////////KONTROL//////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100 + 2*aralik, 150, 400 -aralik, 150, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 + aralik, 150 + aralik, 400, 150 +aralik, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100 + 2*aralik +1, 150 +1, 400 -aralik +1, 150+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 + aralik + 1, 150 + aralik +1, 400 +1, 150 +aralik+1, Kara_Kutu);
        
        ///////////////////////////KONTROL//////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100 + 2*aralik, 50, 100 + 2*aralik,150, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 + aralik, 50 + aralik, 100 + aralik, 150 +aralik, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100 + 2*aralik +1, 50+1, 100 + 2*aralik+1,150+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 + aralik +1, 50 + aralik+1, 100 + aralik+1, 150 +aralik+1, Kara_Kutu);
        
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(400-aralik, 50, 400 -aralik, 150, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(400, 50+aralik, 400, 150+aralik, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(400-aralik+1, 50+1, 400 -aralik+1, 150+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(400+1, 50+aralik+1, 400+1, 150+aralik+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100-aralik, 50, 100 + 2*aralik, 50, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100, 50+aralik, 100 +aralik, 50+aralik, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100-aralik +1, 50+1, 100 + 2*aralik+1, 50+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100+1, 50+aralik+1, 100 +aralik+1, 50+aralik+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100, 50+aralik, 100, 150, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100-aralik, 50, 100-aralik, 150, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100 +1 , 50+aralik +1, 100+1, 150+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100-aralik +1, 50+1, 100-aralik+1, 150+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100-aralik, 150, 100, 150, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100-aralik+1, 150+1, 100+1, 150+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
         //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(400, 50 +aralik, 500 , 50 +aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(400 - aralik, 50, 500 +aralik, 50, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(400+1, 50 +aralik+1, 500+1 , 50 +aralik+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(400 - aralik+1, 50+1, 500 +aralik+1, 50+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100 - aralik, 150+aralik, 100, 150+aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 - aralik+1, 150+aralik+1, 100+1, 150+aralik+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100 - aralik, 150+aralik, 100 - aralik, 250 + aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100, 150+aralik, 100, 250, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100 - aralik+1, 150+aralik+1, 100 - aralik+1, 250 + aralik+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100+1, 150+aralik+1, 100+1, 250+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(500 +aralik, 50, 500 +aralik, 250 +aralik, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(500, 50 +aralik, 500, 250, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(500 +aralik+1, 50+1, 500 +aralik+1, 250 +aralik+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(500+1, 50 +aralik+1, 500+1, 250+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////KONTROL/////////////////////////////////////
        Kara_Kutu=Line_To_Dizi(100, 250, 500, 250, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 - aralik, 250 + aralik, 500 +aralik, 250 + aralik, Kara_Kutu);
        /******************/
        Kara_Kutu=Line_To_Dizi(100+1, 250+1, 500+1, 250+1, Kara_Kutu);
        Kara_Kutu=Line_To_Dizi(100 - aralik+1, 250 + aralik+1, 500 +aralik+1, 250 + aralik+1, Kara_Kutu);
        //////////////////////////////////////////////////////////////////////////
        
    }

   
    
    
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(ayarla);
        animasyon.start();
        
        if(buton_aktif)
        {
            
            
            g2d.setColor(Color.GREEN);
            g2d.drawString("Basla",bitis1.x,bitis1.y);
            g2d.drawString("Bitis",basla1.x,basla1.y);
            
            g2d.setColor(Color.RED);
            g2d.drawLine(0, 0, 500 +2* aralik, 0);//kırmızı üst
            g2d.drawLine(0, 250 + 2*aralik, 500 +2* aralik, 250 + 2*aralik);//kırmızı alt
            g2d.drawLine(0, 0, 0, 250 + 2*aralik);//kırmızı sol
            g2d.drawLine(500 +2* aralik, 0, 500 +2* aralik, 250 + 2*aralik); // kırmızı sag
            
            
            
           
            g2d.setColor(Color.BLUE);
            ////////////////////////Üst sol düzlük///////////////////////////////////
            g2d.drawLine(100 + 2*aralik, 150, 400 -aralik, 150);
            g2d.drawLine(100 + aralik, 150 + aralik, 400, 150 +aralik); 
            /********************************************/
            g2d.drawLine(100 + 2*aralik +1, 150 +1, 400 -aralik +1, 150+1);
            g2d.drawLine(100 + aralik + 1, 150 + aralik +1, 400 +1, 150 +aralik+1); 
            
            ////////////////////////////UST Yukarı Diklik/////////////////////////
            g2d.drawLine(100 + 2*aralik, 150, 100 + 2*aralik,50);
            g2d.drawLine(100 + aralik, 150 + aralik, 100 + aralik, 50 +aralik);
            /********************************************/
            g2d.drawLine(100 + 2*aralik +1, 150+1, 100 + 2*aralik+1,50+1);
            g2d.drawLine(100 + aralik +1, 150 + aralik+1, 100 + aralik+1, 50 +aralik+1);
           
            
            ///////////////////////// sol üst dik/////////////////////////////////////////           
            g2d.drawLine(400-aralik, 150, 400 -aralik, 50);//
            g2d.drawLine(400, 150+aralik, 400, 50+aralik);
            /********************************************/
            g2d.drawLine(400-aralik+1, 150+1, 400 -aralik+1, 50+1);
            g2d.drawLine(400+1, 150+aralik+1, 400+1, 50+aralik+1);
            //////////////////////////Sol ÜSt Düzlük/////////////////////////
            
            g2d.drawLine(100-aralik, 50, 100 + 2*aralik, 50);//
            g2d.drawLine(100, 50+aralik, 100 +aralik, 50+aralik);
            /********************************************/
            g2d.drawLine(100-aralik +1, 50+1, 100 + 2*aralik+1, 50+1);//
            g2d.drawLine(100+1, 50+aralik+1, 100 +aralik+1, 50+aralik+1);
           
            /////////////////////Sol Üst Asagi Düzlük//////////////
            g2d.drawLine(100, 50+aralik, 100, 150);//
            g2d.drawLine(100-aralik, 50, 100-aralik, 150);//
            /********************************************/
            g2d.drawLine(100 +1 , 50+aralik +1, 100+1, 150+1);//
            g2d.drawLine(100-aralik +1, 50+1, 100-aralik+1, 150+1);//
            //////////////////////////////Cıkıs Kapama//////////////////////
            g2d.drawLine(100-aralik, 150, 100, 150);//
            /********************************************/
            g2d.drawLine(100-aralik+1, 150+1, 100+1, 150+1);//
            
            /////////////////////Sag üst düzlük////////////////////////////
            g2d.drawLine(400, 50 +aralik, 500 , 50 +aralik);
            g2d.drawLine(400 - aralik, 50, 500 +aralik, 50);
            /********************************************/
            g2d.drawLine(400+1, 50 +aralik+1, 500+1 , 50 +aralik+1);
            g2d.drawLine(400 - aralik+1, 50+1, 500 +aralik+1, 50+1);

            //////////////////////Giris Kapama//////////////////
            g2d.drawLine(100 - aralik, 150+aralik, 100, 150+aralik);
            /********************************************/
            g2d.drawLine(100 - aralik+1, 150+aralik+1, 100+1, 150+aralik+1);
            /////////////////////Sol Diklik//////////////////////
            g2d.drawLine(100 - aralik, 150+aralik, 100 - aralik, 250 + aralik); 
            g2d.drawLine(100, 150+aralik, 100, 250);
            /********************************************/
            g2d.drawLine(100 - aralik+1, 150+aralik+1, 100 - aralik+1, 250 + aralik+1); 
            g2d.drawLine(100+1, 150+aralik+1, 100+1, 250+1); 

            //////////////////Sag Diklik///////////////////////
            g2d.drawLine(500 +aralik, 50, 500 +aralik, 250 +aralik);//sag dıs
            g2d.drawLine(500, 50 +aralik, 500, 250);//sag iç
            /********************************************/
            g2d.drawLine(500 +aralik+1, 50+1, 500 +aralik+1, 250 +aralik+1);//sag dıs
            g2d.drawLine(500+1, 50 +aralik+1, 500+1, 250+1);//sag iç
            
            ///////////////////////Alt Düzlülk//////////////////////////////
            g2d.drawLine(100, 250, 500, 250); // alt iç
            g2d.drawLine(100 - aralik, 250 + aralik, 500 +aralik, 250 + aralik); //alt dıs
            /********************************************/
            g2d.drawLine(100+1, 250+1, 500+1, 250+1); // alt iç
            g2d.drawLine(100 - aralik+1, 250 + aralik+1, 500 +aralik+1, 250 + aralik+1); //alt dıs

            ////////////////////////////////////Noktaları Ekleme//////////////////////////////////////////////////////
            
           
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
//            g2d.setColor(Color.RED);
//            for(int i=0;i<Kara_Kutu.size();i++)
//            {
//                g2d.drawRect(Kara_Kutu.get(i).x, Kara_Kutu.get(i).y, 1, 1);
//            }
            
            
            
            if(engelekleme)
            {
                g2d.drawLine(450, 70, 450 , 50 +aralik);//engel1
                g2d.drawLine(300, 250, 300, 280); //engel2
            }
           
            
            
            if(sayac < Gecis_Noktalari1.size())
            {
                int x = Gecis_Noktalari1.get(sayac).x - 10;
                int y = Gecis_Noktalari1.get(sayac).y - 10;
                icon.paintIcon(this, g, x, y);
                //System.out.println("gecilen noktalar: > x: " + x + " y: " + y  );
               
            }
            
            if(bot_durum == true)
            {
                if(sayac < Gecis_Noktalari2.size())
                {
                    int x = Gecis_Noktalari2.get(sayac).x - 10;
                    int y = Gecis_Noktalari2.get(sayac).y - 10;
                    icon2.paintIcon(this, g, x, y);
                    //System.out.println("gecilen noktalar: > x: " + x + " y: " + y  );
                    ikinci_x = Gecis_Noktalari2.get(sayac).x;
                    ikinci_y = Gecis_Noktalari2.get(sayac).y;
                    
                }
            }
            else
            {
                icon2.paintIcon(this, g, ikinci_x -10, ikinci_y-10);
            }
            
           
            
            //

            sayac++;
            

        }
                
    }
    
    private boolean  Varmi(Stack<Point> dizi,int x,int y)
    {
        int bulundu=0;
        for(int i=0;i<dizi.size();i++)
        {
            if(dizi.get(i).x == x && dizi.get(i).y == y)
            {
                bulundu = 1;
                break;
            }
        }
        
        if(bulundu == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    private Stack<Point> Line_To_Dizi(int si, int sj, int ei, int ej,Stack<Point> dizi)
    {
        Point gecici;
        Stack<Point> dizi_dondur = dizi;
        if(si==ei)
        {
            for(int i=0;i<=Math.abs(ej-sj);i++)
            {
                
                if(Varmi(dizi,si,(sj+i)) == false)
                {
                    gecici=new Point();
                    gecici.x = si;
                    gecici.y = (sj+i);
                    //System.out.println("x: " + gecici.x + " y: " + gecici.y);
                    dizi_dondur.push(gecici);
                    
                }
               
               
            }
        }
        else if(sj==ej)
        {
            for(int i=0;i<=Math.abs(ei-si);i++)
            {
                if(Varmi(dizi, (si+i), sj) == false)
                { 
                    gecici=new Point();
                    gecici.x =(si+i);
                    gecici.y = sj;
                    //System.out.println("x: " + gecici.x + " y: " + gecici.y);
                    dizi_dondur.push(gecici);
                }
                
            }
        }
        else
        {
            //System.out.println("x,y ->i " + si +"," + sj + " " + "x,y ->j " + ei +"," + ej);
            
            for(int i=0;i<=Math.abs(ei-si);i++)
            {
                if(Varmi(dizi, (si+i), (sj+i)) == false)
                {
                    gecici=new Point();
                    gecici.x = (si+i);
                    gecici.y = (sj+i);
                   // System.out.println("x: " + gecici.x + " y: " + gecici.y);
                    dizi_dondur.push(gecici);
                }
                
            }
        }
        
        return dizi_dondur;
    }

 
  
    
    public Point Uygun_Bul(int x,int y)
    {
        int bul_x=0,bul_y=0;
        Point gecici = new Point(x,y);
        for(int i=-1;i<=1;i++)
        {
            for(int j=-1;j<=1;j++)
            {
                if(Varmi(Kara_Kutu, x+i, y+j)==false)
                {
                    bul_x=i;
                    bul_y=j;           
                    break;
                }
            }
        }
        gecici.x=gecici.x+bul_x;
        gecici.y=gecici.y+bul_y;
        
        
        return gecici;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        this.requestFocusInWindow();

    }
    public class Klavye_Isler implements KeyListener
    {
        Point gecici = new Point(0,0);
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            
            int key = e.getKeyCode();
            if(  Varmi(Kara_Kutu, ikinci_x, ikinci_y)==false)
            {
                if(key == KeyEvent.VK_LEFT)
                {
                    ikinci_x-=1;
                }
                else if(key == KeyEvent.VK_RIGHT)
                {
                    ikinci_x+=1;
                }
                if(key == KeyEvent.VK_UP)
                {
                    ikinci_y-=1;
                }
                else if(key == KeyEvent.VK_DOWN)
                {
                     ikinci_y+=1;
                }
            }
            else
            {
                gecici=Uygun_Bul(ikinci_x,ikinci_y);
                ikinci_x = gecici.x;
                ikinci_y = gecici.y;
            }
                

            repaint();   
                
            
           
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
           
           
        }
        
    }
    
    class Dinleyici implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            
            Component gec = e.getComponent();
            String hangibuton = gec.getName();

            if(hangibuton.equalsIgnoreCase("Basla"))
            {
                buton_aktif = true;
                Yaris_Baslat();
            }
            else if(hangibuton.equalsIgnoreCase("Engel_Ekle"))
            {
                engelekleme = true;
                Kara_Kutu=Line_To_Dizi(449, 70, 449 , 50 +aralik, Kara_Kutu);
                Kara_Kutu=Line_To_Dizi(450, 70, 450 , 50 +aralik, Kara_Kutu);
                Kara_Kutu=Line_To_Dizi(451, 70, 451 , 50 +aralik, Kara_Kutu);
                
                Kara_Kutu=Line_To_Dizi(299, 250, 299, 280, Kara_Kutu);
                Kara_Kutu=Line_To_Dizi(300, 250, 300, 280, Kara_Kutu);
                Kara_Kutu=Line_To_Dizi(301, 250, 301, 281, Kara_Kutu);
                

                System.out.println("Engel Ekleme Basarili");

            }
            else if(hangibuton.equalsIgnoreCase("Oge_Ekle"))
            {
                NoktaEkleme();
                System.out.println("Oge Ekleme Basarili");
            }
            else if(hangibuton.equalsIgnoreCase("Kullanici"))
            {
                if(bot_durum==false)
                {
                    botlu.setText("Bilgisayar");
                    bot_durum=true;
                }
                else
                {
                    botlu.setText("Kullanici");
                    bot_durum=false;
                }
                
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

   
    }
    
    
}
