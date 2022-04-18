/*
 * generated by Xtext 2.25.0
 */
package dk.sdu.mmmi.mdsd.generator

import dk.sdu.mmmi.mdsd.math.Div
import dk.sdu.mmmi.mdsd.math.LetBinding
import dk.sdu.mmmi.mdsd.math.MathExp
import dk.sdu.mmmi.mdsd.math.MathNumber
import dk.sdu.mmmi.mdsd.math.Minus
import dk.sdu.mmmi.mdsd.math.Mult
import dk.sdu.mmmi.mdsd.math.Plus
import dk.sdu.mmmi.mdsd.math.VarBinding
import dk.sdu.mmmi.mdsd.math.VariableUse
import java.util.HashMap
import java.util.Map
import javax.swing.JOptionPane
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import dk.sdu.mmmi.mdsd.math.Program

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class MathGenerator extends AbstractGenerator {
	
	static Map<String, Integer> variables;

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val className = resource.allContents.filter(Program).next
		val math = resource.allContents.filter(MathExp).next
		val variables = math.variables
		val result = math.compute
		result.displayPanel
		var counter = 0;
		fsa.generateFile("C:\\Users\\Peter\\Documents\\GitHub\\my_assignment3\\math_expression\\src\\math_expression", '''
		package math_expression;
		public class «className.name» {
		
		«FOR variable : variables»
		public int «variable.name»;
		«ENDFOR»
		
		«IF math.externals.size > 0»
		  private External external;
		  
		  public «className.name»(External external) {
		    this.external = external
		  }
		  «ENDIF»
		
		  public void compute() {
		    x = 2 + 2;
		    y = this.external.sqrt(x);
		  }
		
		«IF math.externals.size > 0»
		  interface External {
		  	«FOR external : math.externals»
		    public int «external.name»(«FOR param : external.parameters SEPARATOR ', '»«param» n«counter++»«ENDFOR»);
		    «ENDFOR»
		  }
		  «ENDIF»
		}
		''')
		
	}
		
	def void displayPanel(Map<String, Integer> result) {
		var resultString = ""
		for (entry : result.entrySet()) {
         	resultString += "var " + entry.getKey() + " = " + entry.getValue() + "\n"
        }
		
		JOptionPane.showMessageDialog(null, resultString ,"Math Language", JOptionPane.INFORMATION_MESSAGE)
	}
	
	def static compute(MathExp math) {
		variables = new HashMap()
		for(varBinding: math.variables)
			varBinding.computeExpression()
		variables
	}
	
	def static dispatch int computeExpression(VarBinding binding) {
		variables.put(binding.name, binding.expression.computeExpression())
		return variables.get(binding.name)
	}
	
	def static dispatch int computeExpression(MathNumber exp) {
		exp.value
	}

	def static dispatch int computeExpression(Plus exp) {
		exp.left.computeExpression + exp.right.computeExpression
	}
	
	def static dispatch int computeExpression(Minus exp) {
		exp.left.computeExpression - exp.right.computeExpression
	}
	
	def static dispatch int computeExpression(Mult exp) {
		exp.left.computeExpression * exp.right.computeExpression
	}
	
	def static dispatch int computeExpression(Div exp) {
		exp.left.computeExpression / exp.right.computeExpression
	}

	def static dispatch int computeExpression(LetBinding exp) {
		exp.body.computeExpression
	}
	
	def static dispatch int computeExpression(VariableUse exp) {
		exp.ref.computeBinding
	}

	def static dispatch int computeBinding(VarBinding binding){
		if(!variables.containsKey(binding.name))
			binding.computeExpression()			
		variables.get(binding.name)
	}
	
	def static dispatch int computeBinding(LetBinding binding){
		binding.binding.computeExpression
	}
	
}
