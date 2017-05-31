//
//  edcode.cpp
//  vntoeic
//
//  Created by dai nguyen on 5/21/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#include "edcode.hpp"

char * decode(const void * in){
    
    const char * k1 ="anhdaideptrai201292bachkhoahcm92";
    Byte_Block<32>key(k1,strlen(k1));
    AES256_Base ase(key);
    const char * in1 = static_cast<const char * >(in);

    int *count =(int *)in1;
    int y = *count;
    char * out = new char[y];
    int n =0;
    while (n!=(y-5)) {
        Byte_Block<16>*in2 = new Byte_Block<16>(in1+4+n,16);
        ase.decrypt(*in2);
        for(int i=0;i<16;i++){
            out[n+4+i]=in2->cdata()[i];
        }
        n+=16;
    }
    
    while(y>0){
        if (out[y+4]=='\200'){
            out[y+4]='\0';
            break;
        }else y--;
    }
    return out+4;
}
