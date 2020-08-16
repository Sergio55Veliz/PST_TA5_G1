package com.example.amst1;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AsyncQuery extends AsyncTask<String[],Void,String[]> {
    @Override
    protected String[] doInBackground(String[]... datos) {
        //return new String[0];
        String[] totalResultadoSQL = null;
        String type = datos[0][0];
        String login_url = datos[0][1];

        if(type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);

                InputStream iStr = httpURLConnection.getInputStream();
                BufferedReader bR = new BufferedReader(new InputStreamReader(iStr,"UTF-8"));
                String resultado="";
                String line="";

                while((line = bR.readLine()) != null){
                    resultado += line + System.getProperty("line.separator") ;
                }
                bR.close();
                iStr.close();
                httpURLConnection.disconnect();

                totalResultadoSQL = new String[]{
                        resultado
                };

            } catch (MalformedURLException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("query")){
            try {
                String query = datos[0][2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postdata = URLEncoder.encode("SQL","UTF-8")+"="+URLEncoder.encode(query,"UTF-8");
                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String resultado="";
                String line="";

                while((line = bufferedReader.readLine()) != null){
                    resultado += line + System.getProperty("line.separator");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                totalResultadoSQL = new String[]{
                        resultado
                };

            } catch (MalformedURLException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return totalResultadoSQL;
    }

}