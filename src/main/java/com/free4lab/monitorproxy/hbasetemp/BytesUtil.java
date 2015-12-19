package com.free4lab.monitorproxy.hbasetemp;

import java.nio.ByteBuffer;
import java.sql.Timestamp;

public class BytesUtil {
    public static byte[] toBytes(ByteBuffer bf) {
    	if(bf == null) {
    		return new byte[0];
    	}
        if (bf.position() == 0 && bf.limit() == bf.capacity()) {
            return (byte[]) bf.array();
        }
        int position = bf.position();
        int len = bf.limit() - bf.position();
        byte[] ret = new byte[len];
        bf.get(ret, 0, len);
        bf.position(position);
        return ret;
    }
    
    public static byte[] toBytes(long l){
        byte[] bytes = new byte[8];
        bytes[7]= (byte) (l       & 0xff);
        bytes[6]= (byte) ((l>>8 ) & 0xff) ;
        bytes[5]= (byte) ((l>>16) & 0xff) ;
        bytes[4]= (byte) ((l>>24) & 0xff) ;
        bytes[3]= (byte) ((l>>32) & 0xff) ;
        bytes[2]= (byte) ((l>>40) & 0xff) ;
        bytes[1]= (byte) ((l>>48) & 0xff) ;
        bytes[0]= (byte) ((l>>56) & 0xff) ;
        return bytes;
    }
    
    public static long toLong(byte[] bytes){
        long l = 0; 
        l |= (bytes[0] & 0xffL) << 56;
        l |= (bytes[1] & 0xffL) << 48;
        l |= (bytes[2] & 0xffL) << 40;
        l |= (bytes[3] & 0xffL) << 32;
        l |= (bytes[4] & 0xffL) << 24;
        l |= (bytes[5] & 0xffL) << 16;
        l |= (bytes[6] & 0xffL) << 8;
        l |= (bytes[7] & 0xffL) ;
        
        return l;
    }
    
    public static byte[] toBytes(int l){
        byte[] bytes = new byte[4];
        bytes[3]= (byte) (l       & 0xff);
        bytes[2]= (byte) ((l>>8 ) & 0xff) ;
        bytes[1]= (byte) ((l>>16) & 0xff) ;
        bytes[0]= (byte) ((l>>24) & 0xff) ;
        return bytes;
    }
    
    public static void main(String[] args) {
		System.out.println(BytesUtil.toBytes(null));
		System.out.println(new Timestamp(0));
	}
}
