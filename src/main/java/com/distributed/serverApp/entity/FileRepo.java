package com.distributed.serverApp.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*; 
public interface FileRepo extends Remote{  
    public File download(String fileName) throws RemoteException,FileNotFoundException,IOException;  
}  

