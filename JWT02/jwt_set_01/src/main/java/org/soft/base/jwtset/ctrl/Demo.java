package org.soft.base.jwtset.ctrl;

public class Demo {
    interface demo01{
        default  void one(){
            System.out.println("<UNK>1");
        }
    }

    interface demo02{
        default  void one(){
            System.out.println("<UNK>2");
        }
    }

    static class All  implements demo02 , demo01{

        @Override
        public void one() {
            demo01.super.one();
        }
    }

    public static void main(String[] args) {

        All all = new All();
        all.one();
    }
}
interface  d{
    default  String ok(){
        return "ok";
    }
}