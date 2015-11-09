package prueba;

/**
 *
 * @author JCORREA
 */
public class Automata {
    
    public static void main(String [] args){
    char [] text = new char[]{'a', 'b','c','a', 'b','c','a','b','b',' '};
    char nodo = 'i';
    int c = 0;
    
    System.out.println(nodo);
    while(true){
	if(nodo == 'i'){
            if(text[c] == 'a'){
		nodo='p';
		text[c] = 'b';
            }else if (text[c] == 'b'){
		nodo='p';
		text[c] = 'a';
            }else if (text[c] == 'c'){
		nodo='q';
		c=c+1;
            }else{ break; }
        System.out.println(nodo);            
        }else if(nodo == 'p'){
		if(text[c] == 'a' || text[c] == 'b'){
			nodo = 'i';
			c=c+1;
                }else{break;}
	System.out.println(nodo);			
        }else if(nodo == 'q'){
		if(text[c] == 'a'){
			nodo='r';
			text[c] = 'b';
                }else if (text[c] == 'b'){
			nodo='r';
			text[c] = 'a';
		}else if( text[c] == 'c'){
			nodo='s';
			c=c-1;
                }else break;
	System.out.println(nodo);		
        }else if (nodo == 'r'){
		if (text[c] == 'a' || text[c] == 'b'){
			nodo = 'q';
			c=c+1;
                }else break;
        System.out.println(nodo);
	}else if( nodo == 's'){
		if (text[c] == 'a'){
			c=c-1;
		}else if( text[c] == 'b'){
			c=c-1;
		}else if( text[c] == 'c'){
			text[c]= ' ';
		}else if( text[c] == ' '){
			nodo = 't';
			c=c+1;
                }else break;
	System.out.println(nodo);		
	}else if( nodo == 't'){
		if (text[c] == 'a'){
			c=c+1;
		}else if( text[c] == 'b'){
			c=c+1;
		}else if( text[c] == 'c'){
			nodo = 'q';
			c=c+1;
                }else break;
        System.out.println(nodo);
        }
    
    }
        System.out.println(text);
    }
    
}
    
