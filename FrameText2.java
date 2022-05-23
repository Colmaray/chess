//package view;
//
//import model.ChessComponent;
//
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class FrameText2 {
//    //public static final Frame f = new Frame("改变背景颜色");
//    public static void changeColor(ChessComponent f) {
//
//        // 创建窗口对象
//        // 设置窗口的属性
//        f.setBounds(300, 300, 400, 400);
//        // 设置窗口布局
//        f.setLayout(new FlowLayout());
//
////        // 创建按钮对象
////        Button b1 = new Button("红色");
////        Button b2 = new Button("橙色");
////
////        // 添加按钮组件到窗体中
////        f.add(b1);
////        f.add(b2);
//
//        // 设置窗口关闭监听事件
//        f.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//
//        // 设置鼠标进入按钮区域的监听事件
//        // 这个监听事件监听的对象是鼠标 :MouseListener MouseAdapter
//        b1.addMouseListener(new MouseAdapter() {
//            // public void mouseEntered(MouseEvent e)鼠标进入到组件上时调用。
//            public void mouseEntered(MouseEvent e) {
//                f.setBackground(Color.RED);
//            }
//        });
//        // 设置鼠标离开按钮区域的监听时间
//        b1.addMouseListener(new MouseAdapter() {
//            // public void mouseExited(MouseEvent e)鼠标离开组件时调用。
//            public void mouseExited(MouseEvent e) {
//                f.setBackground(Color.white);
//            }
//        });
//
//        // 设置鼠标进入按钮区域的监听事件
//        // 这个监听事件监听的对象是鼠标 :MouseListener MouseAdapter
//        b2.addMouseListener(new MouseAdapter() {
//            // public void mouseEntered(MouseEvent e)鼠标进入到组件上时调用。
//            public void mouseEntered(MouseEvent e) {
//                f.setBackground(Color.ORANGE);
//            }
//        });
//        // 设置鼠标离开按钮区域的监听事件
//        b2.addMouseListener(new MouseAdapter() {
//            // public void mouseExited(MouseEvent e)鼠标离开组件时调用。
//            public void mouseExited(MouseEvent e) {
//                f.setBackground(Color.white);
//            }
//        });
//
//        // 设置显示窗口
//        f.setVisible(true);
//
//    }
//
//}
