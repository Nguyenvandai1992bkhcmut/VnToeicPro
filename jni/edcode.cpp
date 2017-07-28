//
//  edcode.cpp
//  vntoeic
//
//  Created by dai nguyen on 5/21/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#include "edcode.hpp"

char * decode(const void * in){
    if(in == NULL) return (char *)"";
    const char * k1 ="anhdaideptrai201292bachkhoahcm92";
    Byte_Block<32>key(k1,strlen(k1));
    AES256_Base ase(key);
   const char * in1 = static_cast<const char * >(in);

    int y  =*(int *)in1;
    char * out = new char[y+1];
    int n =0;
    while (n!=y) {
        Byte_Block<16>in2(in1+4+n,16);
        ase.decrypt(in2);
        for(int i=0;i<16;i++){
            out[n+i]=in2.cdata()[i];
        }
        n+=16;
    }
    out[y]='\0';
   while(y>0){
        if (out[y]=='\200'){
            out[y]='\0';
            break;
        }else y--;
    }
    return out;
}
