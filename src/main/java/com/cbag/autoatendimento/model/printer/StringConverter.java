package com.cbag.autoatendimento.model.printer;

public class StringConverter {

    public static byte[] toIbm437(String texto){
        byte[] stringAsBytes = new byte[texto.length()]; // cada caractere tem 1 byte, então funciona
        for(int i = 0; i < texto.length(); i++){
            char c = texto.charAt(i);
            switch(c){
                case(' '):
                    stringAsBytes[i] = 0x20;
                    break;
                case('!'):
                    stringAsBytes[i] = 0x21;
                    break;
                case('"'):
                    stringAsBytes[i] = 0x22;
                    break;
                case('#'):
                    stringAsBytes[i] = 0x23;
                    break;
                case('$'):
                    stringAsBytes[i] = 0x24;
                    break;
                case('%'):
                    stringAsBytes[i] = 0x25;
                    break;
                case('&'):
                    stringAsBytes[i] = 0x26;
                    break;
                case('\''):
                    stringAsBytes[i] = 0x27;
                    break;
                case('('):
                    stringAsBytes[i] = 0x28;
                    break;
                case(')'):
                    stringAsBytes[i] = 0x29;
                    break;
                case('*'):
                    stringAsBytes[i] = 0x2a;
                    break;
                case('+'):
                    stringAsBytes[i] = 0x2b;
                    break;
                case(','):
                    stringAsBytes[i] = 0x2c;
                    break;
                case('-'):
                    stringAsBytes[i] = 0x2d;
                    break;
                case('.'):
                    stringAsBytes[i] = 0x2e;
                    break;
                case('/'):
                    stringAsBytes[i] = 0x2f;
                    break;
                case('0'):
                    stringAsBytes[i] = 0x30;
                    break;
                case('1'):
                    stringAsBytes[i] = 0x31;
                    break;
                case('2'):
                    stringAsBytes[i] = 0x32;
                    break;
                case('3'):
                    stringAsBytes[i] = 0x33;
                    break;
                case('4'):
                    stringAsBytes[i] = 0x34;
                    break;
                case('5'):
                    stringAsBytes[i] = 0x35;
                    break;
                case('6'):
                    stringAsBytes[i] = 0x36;
                    break;
                case('7'):
                    stringAsBytes[i] = 0x37;
                    break;
                case('8'):
                    stringAsBytes[i] = 0x38;
                    break;
                case('9'):
                    stringAsBytes[i] = 0x39;
                    break;
                case(':'):
                    stringAsBytes[i] = 0x3a;
                    break;
                case(';'):
                    stringAsBytes[i] = 0x3b;
                    break;
                case('<'):
                    stringAsBytes[i] = 0x3c;
                    break;
                case('='):
                    stringAsBytes[i] = 0x3d;
                    break;
                case('>'):
                    stringAsBytes[i] = 0x3e;
                    break;
                case('?'):
                    stringAsBytes[i] = 0x3f;
                    break;
                case('@'):
                    stringAsBytes[i] = 0x40;
                    break;
                case('A'):
                    stringAsBytes[i] = 0x41;
                    break;
                case('Ã'):
                    stringAsBytes[i] = 0x41;
                    break;
                case('B'):
                    stringAsBytes[i] = 0x42;
                    break;
                case('C'):
                    stringAsBytes[i] = 0x43;
                    break;
                case('D'):
                    stringAsBytes[i] = 0x44;
                    break;
                case('E'):
                    stringAsBytes[i] = 0x45;
                    break;
                case('F'):
                    stringAsBytes[i] = 0x46;
                    break;
                case('G'):
                    stringAsBytes[i] = 0x47;
                    break;
                case('H'):
                    stringAsBytes[i] = 0x48;
                    break;
                case('I'):
                    stringAsBytes[i] = 0x49;
                    break;
                case('J'):
                    stringAsBytes[i] = 0x4a;
                    break;
                case('K'):
                    stringAsBytes[i] = 0x4b;
                    break;
                case('L'):
                    stringAsBytes[i] = 0x4c;
                    break;
                case('M'):
                    stringAsBytes[i] = 0x4d;
                    break;
                case('N'):
                    stringAsBytes[i] = 0x4e;
                    break;
                case('O'):
                    stringAsBytes[i] = 0x4f;
                    break;
                case('P'):
                    stringAsBytes[i] = 0x50;
                    break;
                case('Q'):
                    stringAsBytes[i] = 0x51;
                    break;
                case('R'):
                    stringAsBytes[i] = 0x52;
                    break;
                case('S'):
                    stringAsBytes[i] = 0x53;
                    break;
                case('T'):
                    stringAsBytes[i] = 0x54;
                    break;
                case('U'):
                    stringAsBytes[i] = 0x55;
                    break;
                case('V'):
                    stringAsBytes[i] = 0x56;
                    break;
                case('W'):
                    stringAsBytes[i] = 0x57;
                    break;
                case('X'):
                    stringAsBytes[i] = 0x58;
                    break;
                case('Y'):
                    stringAsBytes[i] = 0x59;
                    break;
                case('Z'):
                    stringAsBytes[i] = 0x5a;
                    break;
                case('['):
                    stringAsBytes[i] = 0x5b;
                    break;
                case('\\'):
                    stringAsBytes[i] = 0x5c;
                    break;
                case(']'):
                    stringAsBytes[i] = 0x5d;
                    break;
                case('^'):
                    stringAsBytes[i] = 0x5e;
                    break;
                case('_'):
                    stringAsBytes[i] = 0x5f;
                    break;
                case('‛'):
                    stringAsBytes[i] = 0x60;
                    break;
                case('a'):
                    stringAsBytes[i] = 0x61;
                    break;
                case('ã'):
                    stringAsBytes[i] = 0x61;
                    break;
                case('b'):
                    stringAsBytes[i] = 0x62;
                    break;
                case('c'):
                    stringAsBytes[i] = 0x63;
                    break;
                case('d'):
                    stringAsBytes[i] = 0x64;
                    break;
                case('e'):
                    stringAsBytes[i] = 0x65;
                    break;
                case('f'):
                    stringAsBytes[i] = 0x66;
                    break;
                case('g'):
                    stringAsBytes[i] = 0x67;
                    break;
                case('h'):
                    stringAsBytes[i] = 0x68;
                    break;
                case('i'):
                    stringAsBytes[i] = 0x69;
                    break;
                case('j'):
                    stringAsBytes[i] = 0x6a;
                    break;
                case('k'):
                    stringAsBytes[i] = 0x6b;
                    break;
                case('l'):
                    stringAsBytes[i] = 0x6c;
                    break;
                case('m'):
                    stringAsBytes[i] = 0x6d;
                    break;
                case('n'):
                    stringAsBytes[i] = 0x6e;
                    break;
                case('o'):
                    stringAsBytes[i] = 0x6f;
                    break;
                case('p'):
                    stringAsBytes[i] = 0x70;
                    break;
                case('q'):
                    stringAsBytes[i] = 0x71;
                    break;
                case('r'):
                    stringAsBytes[i] = 0x72;
                    break;
                case('s'):
                    stringAsBytes[i] = 0x73;
                    break;
                case('t'):
                    stringAsBytes[i] = 0x74;
                    break;
                case('u'):
                    stringAsBytes[i] = 0x75;
                    break;
                case('v'):
                    stringAsBytes[i] = 0x76;
                    break;
                case('w'):
                    stringAsBytes[i] = 0x77;
                    break;
                case('x'):
                    stringAsBytes[i] = 0x78;
                    break;
                case('y'):
                    stringAsBytes[i] = 0x79;
                    break;
                case('z'):
                    stringAsBytes[i] = 0x7a;
                    break;
                case('{'):
                    stringAsBytes[i] = 0x7b;
                    break;
                case('|'):
                    stringAsBytes[i] = 0x7c;
                    break;
                case('}'):
                    stringAsBytes[i] = 0x7d;
                    break;
                case('~'):
                    stringAsBytes[i] = 0x7e;
                    break;
                case('Ç'):
                    stringAsBytes[i] = (byte)0x80;
                    break;
                case('ü'):
                    stringAsBytes[i] = (byte)0x81;
                    break;
                case('é'):
                    stringAsBytes[i] = (byte)0x82;
                    break;
                case('â'):
                    stringAsBytes[i] = (byte)0x83;
                    break;
                case('ä'):
                    stringAsBytes[i] = (byte)0x84;
                    break;
                case('à'):
                    stringAsBytes[i] = (byte)0x85;
                    break;
                //todo 0x86 é a com bola (angstrom) minúsculo
                case('ç'):
                    stringAsBytes[i] = (byte)0x87;
                    break;
                case('ê'):
                    stringAsBytes[i] = (byte)0x88;
                    break;
                case('ë'):
                    stringAsBytes[i] = (byte)0x89;
                    break;
                case('è'):
                    stringAsBytes[i] = (byte)0x8a;
                    break;
                case('ï'):
                    stringAsBytes[i] = (byte)0x8b;
                    break;
                case('î'):
                    stringAsBytes[i] = (byte)0x8c;
                    break;
                case('ì'):
                    stringAsBytes[i] = (byte)0x8d;
                    break;
                case('Ä'):
                    stringAsBytes[i] = (byte)0x8e;
                    break;
                // todo 8f é angstrom
                case('ô'):
                    stringAsBytes[i] = (byte)0x93;
                    break;
                case('á'):
                    stringAsBytes[i] = (byte)0xa0;
                    break;
                case('í'):
                    stringAsBytes[i] = (byte)0xa1;
                    break;
                case('ó'):
                    stringAsBytes[i] = (byte)0xa2;
                    break;
                case('ú'):
                    stringAsBytes[i] = (byte)0xa3;
                    break;
                // todo falta um monte de caractere
            }
        }
        return stringAsBytes;
    }
}
