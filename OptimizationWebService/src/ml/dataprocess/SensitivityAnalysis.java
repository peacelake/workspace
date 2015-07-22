package ml.dataprocess;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ml.engine.PrefuseTree;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class SensitivityAnalysis {
	
	private Instances data;
	
	public SensitivityAnalysis(Instances d){
		data = d;
		data.setClass(data.attribute(data.numAttributes()-1));;
		System.out.println(data.classIndex());
	}
	
	public void getCorrelationRank() throws Exception{
		CorrelationAttributeEval eval = new CorrelationAttributeEval();
		eval.setOutputDetailedInfo(true);
		eval.buildEvaluator(data);
		double[] correclation = eval.m_correlations;
		for(int i=0; i<data.numAttributes(); i++){
			System.out.print(data.attribute(i).name()+": "+ correclation[i]);
			System.out.println();
		}
		System.out.println(eval.toString());
	}
	
	public void getDecisionTree() throws Exception{
		REPTree tree = new REPTree();
		tree.buildClassifier(data);	
//		System.out.println(tree);
//		System.out.println(tree.graph());

//		TreeVisualizer tv=new TreeVisualizer(null,tree.graph(),new PlaceNode2());
//		JFrame jf=new JFrame("Weka Classifier Tree Visualizer: J48");
//		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		jf.setSize(1600,1200);
//		jf.getContentPane().setLayout(new BorderLayout());
//		jf.getContentPane().add(tv,BorderLayout.CENTER);
//		jf.setVisible(true);
//		tv.fitToScreen();
		PrefuseTree testTree = new PrefuseTree();
		testTree.display(tree.graph(),"testTree");
	}
	
	public static void main(String[] args) throws Exception{
		Instances ins = CSV2Instance.getInstance("C:\\Users\\t_xuw\\Documents\\Work\\4_Energy-Study\\3.Optimization\\Demo\\DemoTestSets\\test_data.csv");
		SensitivityAnalysis sen = new SensitivityAnalysis(ins);
		sen.getCorrelationRank();
		sen.getDecisionTree();
	}
}
