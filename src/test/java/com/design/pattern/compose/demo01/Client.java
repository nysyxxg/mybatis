package com.design.pattern.compose.demo01;

public class Client {
	public static void main(String[] args) {

		// 创建根节点组件
		NodeComponent root = new RootNode();
		//创建 三个叶子节点组件
		NodeComponent leaf1= new LeafComponent("孙悟空");
		NodeComponent leaf2= new LeafComponent("龙珠");
		NodeComponent leaf3= new LeafComponent("帅哥威");

		//  将叶子节点组件添加到根节点组件中
		root.addComposite(leaf1);
		root.addComposite(leaf2);
		root.addComposite(leaf3);

		// 操作根节点组件，就执行了所有叶子节点组件
		root.operation();
		System.out.println("-------------------------");
//		leaf1.operation();
//		leaf2.operation();
//		leaf3.operation();
	}

}
