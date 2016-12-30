package com.flytxt.imageprocessor.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Result.Image;
import com.google.api.services.customsearch.model.Search;

public class ImageDownloader {
	
	public static void main(String[] args) {
    	
    	 URLConnection conn = null;
         InputStream in = null;
        try{
           /* URL url = new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyBRTyTGFqucQovlc29JPp6Apq-0wQXiTu8&cx=000460103431090730036:nzqcc-ikuge&q=brain&searchType=image&fileType=jpg&imgSize=small&alt=json");
            URLConnection connection = url.openConnection();

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
*/
            
            Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(),null);

            try {
                com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list("brain");
                list.setKey("AIzaSyBRTyTGFqucQovlc29JPp6Apq-0wQXiTu8");
                list.setCx("000460103431090730036:nzqcc-ikuge");
                list.setSearchType("image");
                Search results = list.execute();
                List<Result> items = results.getItems();

                for(Result result:items)
                {
                	Image image = result.getImage();
                	//image.
                  //  System.out.println("Title:"+result.getHtmlTitle());

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
            
            
            
       /*     
            JSONObject json = new JSONObject(builder.toString());
            JSONArray array = json.getJSONArray("items");
            for(int i=0;i<array.length();i++){
            	JSONObject jsonObject = (JSONObject)array.get(i);
               	//BufferedImage image = ImageIO.read(new URL(jsonObject.get("link").toString()));  
               	
                
               	BufferedOutputStream  out = new BufferedOutputStream(new FileOutputStream("f:/shiju/img/Image"+i+".jpg"));
                conn = new URL(jsonObject.get("link").toString()).openConnection();
                in = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int numRead;              
                while ((numRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, numRead);
                    
                }            	
                out.close();
                in.close();
                
                
               	
              }*/
            
          //  BufferedImage image = ImageIO.read(new URL(imageUrl));
          //  JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
        } catch(Exception e){
        	e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}


