package monopolinho.tipos;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Xogador;

public enum TipoCasilla{
    SOLAR(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            Xogador turno=xogo.getTurno();
            String mensaxe="";
            if(next.getEstaHipotecada()){
                mensaxe+="Caiche na casila "+next.getNome()+", pero está hipotecada, non pagas.";
                return mensaxe;
            }else{
                if((!next.getDono().equals(turno))&&(!next.getDono().equals(xogo.getBanca()))){
                    float aPagar;
                    if(next.getGrupo().tenTodoGrupo(next.getDono())){
                        aPagar=next.getAlquiler()* Valor.FACTOR_PAGO_ALQUILER;
                    }else{
                        aPagar=next.getAlquiler();
                    }
                    if(turno.quitarDinheiro(aPagar)){
                        next.getDono().engadirDinheiro(aPagar);
                        mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome();
                        return mensaxe;
                    }else{
                        //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                        mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                        return mensaxe;
                    }
                }
            }
            turno.setPosicion(next);
            return mensaxe;
        }
    },
    SERVIZO(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            Xogador turno=xogo.getTurno();
            String mensaxe="";
            if((!next.getDono().equals(turno)) && (!next.getDono().equals(xogo.getBanca()))){
                float aPagar=valorDados*next.getUsoServizo();
                if(turno.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1){
                    aPagar*=4.0f;
                }else if(turno.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 2){
                    aPagar*=10.0f;
                }
                if(turno.quitarDinheiro(aPagar)){
                    next.getDono().engadirDinheiro(aPagar);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
                }else{
                    //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                    mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                    return mensaxe;
                }
            }
            xogo.getTurno().setPosicion(next);
            return mensaxe;
        }
    },
    TRANSPORTE(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            Xogador turno=xogo.getTurno();
            String mensaxe="";
            if((!next.getDono().equals(turno)) && (!next.getDono().equals(xogo.getBanca()))){
                float aPagar=0;
                aPagar=next.getUsoServizo()*(next.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
                if(turno.quitarDinheiro(aPagar)){
                    next.getDono().engadirDinheiro(aPagar);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
                }else{
                    //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                    mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                    return mensaxe;
                }
            }
            xogo.getTurno().setPosicion(next);
            return mensaxe;
        }
    },
    CARCEL(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            xogo.getTurno().setPosicion(next);
            return "So de visita...";
        }
    },
    IRCARCEL(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            String mensaxe;
            mensaxe="O avatar colocase na casilla CARCEL(TEIXEIRO)";
            xogo.getTurno().setTurnosNaCarcel(3);
            xogo.getTurno().setPosicion(xogo.getTaboeiro().getCasilla(10));
            return mensaxe;
        }
    },
    PARKING(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            xogo.getTurno().engadirDinheiro(xogo.getTaboeiro().getBote());
            String mensaxe="O xogador "+ xogo.getTurno().getNome() + " recibe "+xogo.getTaboeiro().getBote()+", do bote.";
            xogo.getTaboeiro().setBote(0);
            xogo.getTurno().setPosicion(next);
            return mensaxe;
        }
    },
    SALIDA(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            return "";
        }
    },
    IMPOSTO(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            String mensaxe="O xogador "+ xogo.getTurno().getNome() +  " ten que pagar "+next.getImposto() + " por caer en "+next.getNome();
            if(xogo.getTurno().quitarDinheiro(next.getImposto())){
                xogo.getTaboeiro().engadirBote(next.getImposto());
            }else{
                System.err.println("O xogador "+xogo.getTurno().getNome()+" non ten suficiente dinheiro para pagar o imposto");
                //E QUE PASA SE NON TEN CARTOS????????????????????
            }
            xogo.getTurno().setPosicion(next);
            return mensaxe;
        }
    },
    SORTE(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            return "";
        }
    },
    COMUNIDADE(){
        public String interpretarCasilla(Xogo xogo, Casilla next, int valorDados){
            return "";
        }
    };
    public abstract String interpretarCasilla(Xogo xogo, Casilla next, int valorDados);
}
