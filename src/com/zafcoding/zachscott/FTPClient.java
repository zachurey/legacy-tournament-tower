package com.zafcoding.zachscott;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
 
public class FTPClient
{
  private URLConnection m_client;
  private String host;
  private String user;
  private String password;
  private String remoteFile;
  private String erMesg;
  private String succMesg;
 
  public void setHost(String host)
  {
    this.host = host;
  }
 
  public void setUser(String user)
  {
    this.user = user;
  }
 
  public void setPassword(String p)
  {
    this.password = p;
  }
 
  public void setRemoteFile(String d)
  {
    this.remoteFile = d;
  }
 
  public synchronized String getLastSuccessMessage()
  {
    if (this.succMesg == null)
      return "";
    return this.succMesg;
  }
 
  public synchronized String getLastErrorMessage()
  {
    if (this.erMesg == null)
      return "";
    return this.erMesg;
  }
 
  public synchronized boolean uploadFile(String localfilename)
  {
    try
    {
      InputStream is = new FileInputStream(localfilename);
 
      BufferedInputStream bis = new BufferedInputStream(is);
      OutputStream os = this.m_client.getOutputStream();
      BufferedOutputStream bos = new BufferedOutputStream(os);
      byte[] buffer = new byte[1024];
      int readCount;
      while ((readCount = bis.read(buffer)) > 0)
      {
        bos.write(buffer, 0, readCount);
      }
      bos.close();
 
      this.succMesg = "Uploaded!";
 
      return true;
    } catch (Exception ex) {
      StringWriter sw0 = new StringWriter();
      PrintWriter p0 = new PrintWriter(sw0, true);
      ex.printStackTrace(p0);
      this.erMesg = sw0.getBuffer().toString();
    }
    return false;
  }
 
  public synchronized boolean downloadFile(String localfilename)
  {
    try
    {
      InputStream is = this.m_client.getInputStream();
      BufferedInputStream bis = new BufferedInputStream(is);
 
      OutputStream os = new FileOutputStream(localfilename);
      BufferedOutputStream bos = new BufferedOutputStream(os);
 
      byte[] buffer = new byte[1024];
      int readCount;
      while ((readCount = bis.read(buffer)) > 0)
      {
        bos.write(buffer, 0, readCount);
      }
      bos.close();
      is.close();
      this.succMesg = "Downloaded!";
 
      return true;
    } catch (Exception ex) {
      StringWriter sw0 = new StringWriter();
      PrintWriter p0 = new PrintWriter(sw0, true);
      ex.printStackTrace(p0);
      this.erMesg = sw0.getBuffer().toString();
    }
    return false;
  }
 
  public synchronized boolean connect()
  {
    try
    {
      URL url = new URL("ftp://" + this.user + ":" + this.password + "@" + this.host +
        "/" + this.remoteFile + ";type=i");
      this.m_client = url.openConnection();
 
      return true;
    }
    catch (Exception ex) {
      StringWriter sw0 = new StringWriter();
      PrintWriter p0 = new PrintWriter(sw0, true);
      ex.printStackTrace(p0);
      this.erMesg = sw0.getBuffer().toString();
    }return false;
  }
}