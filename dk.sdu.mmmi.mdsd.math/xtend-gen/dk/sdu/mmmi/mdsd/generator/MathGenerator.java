/**
 * generated by Xtext 2.25.0
 */
package dk.sdu.mmmi.mdsd.generator;

import com.google.common.collect.Iterators;
import dk.sdu.mmmi.mdsd.math.Binding;
import dk.sdu.mmmi.mdsd.math.Div;
import dk.sdu.mmmi.mdsd.math.External;
import dk.sdu.mmmi.mdsd.math.LetBinding;
import dk.sdu.mmmi.mdsd.math.MathExp;
import dk.sdu.mmmi.mdsd.math.MathNumber;
import dk.sdu.mmmi.mdsd.math.Minus;
import dk.sdu.mmmi.mdsd.math.Mult;
import dk.sdu.mmmi.mdsd.math.ParmeterTypes;
import dk.sdu.mmmi.mdsd.math.Plus;
import dk.sdu.mmmi.mdsd.math.Program;
import dk.sdu.mmmi.mdsd.math.VarBinding;
import dk.sdu.mmmi.mdsd.math.VariableUse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@SuppressWarnings("all")
public class MathGenerator extends AbstractGenerator {
  private static Map<String, String> variables;
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    final Program className = Iterators.<Program>filter(resource.getAllContents(), Program.class).next();
    final MathExp math = Iterators.<MathExp>filter(resource.getAllContents(), MathExp.class).next();
    final EList<VarBinding> variables = math.getVariables();
    final Map<String, String> result = MathGenerator.compute(math);
    int counter = 0;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package math_expression;");
    _builder.newLine();
    _builder.append("public class ");
    String _name = className.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final VarBinding variable : variables) {
        _builder.append("public int ");
        String _name_1 = variable.getName();
        _builder.append(_name_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      int _size = math.getExternals().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("private External external;");
        _builder.newLine();
        _builder.newLine();
        _builder.append("public ");
        String _name_2 = className.getName();
        _builder.append(_name_2);
        _builder.append("(External external) {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("this.external = external");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("  ");
    _builder.append("public void compute() {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("x = 2 + 2;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("y = this.external.sqrt(x);");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      int _size_1 = math.getExternals().size();
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        _builder.append("interface External {");
        _builder.newLine();
        {
          EList<External> _externals = math.getExternals();
          for(final External external : _externals) {
            _builder.append("public int ");
            String _name_3 = external.getName();
            _builder.append(_name_3);
            _builder.append("(");
            {
              EList<ParmeterTypes> _parameters = external.getParameters();
              boolean _hasElements = false;
              for(final ParmeterTypes param : _parameters) {
                if (!_hasElements) {
                  _hasElements = true;
                } else {
                  _builder.appendImmediate(", ", "");
                }
                _builder.append(param);
                _builder.append(" n");
                int _plusPlus = counter++;
                _builder.append(_plusPlus);
              }
            }
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    fsa.generateFile("C:\\Users\\Peter\\Documents\\GitHub\\my_assignment3\\math_expression\\src\\math_expression", _builder);
  }
  
  public static Map<String, String> compute(final MathExp math) {
    Map<String, String> _xblockexpression = null;
    {
      HashMap<String, String> _hashMap = new HashMap<String, String>();
      MathGenerator.variables = _hashMap;
      EList<VarBinding> _variables = math.getVariables();
      for (final VarBinding varBinding : _variables) {
        MathGenerator.computeExpression(varBinding);
      }
      _xblockexpression = MathGenerator.variables;
    }
    return _xblockexpression;
  }
  
  protected static String _computeExpression(final VarBinding binding) {
    MathGenerator.variables.put(binding.getName(), MathGenerator.computeExpression(binding.getExpression()));
    return MathGenerator.variables.get(binding.getName());
  }
  
  protected static String _computeExpression(final MathNumber exp) {
    return Integer.valueOf(exp.getValue()).toString();
  }
  
  protected static String _computeExpression(final Plus exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = (_computeExpression + " + ");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    return (_plus + _computeExpression_1);
  }
  
  protected static String _computeExpression(final Minus exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = (_computeExpression + " - ");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    return (_plus + _computeExpression_1);
  }
  
  protected static String _computeExpression(final Mult exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = (_computeExpression + " * ");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    return (_plus + _computeExpression_1);
  }
  
  protected static String _computeExpression(final Div exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getLeft());
    String _plus = (_computeExpression + " / ");
    String _computeExpression_1 = MathGenerator.computeExpression(exp.getRight());
    return (_plus + _computeExpression_1);
  }
  
  protected static String _computeExpression(final LetBinding exp) {
    return MathGenerator.computeExpression(exp.getBody());
  }
  
  protected static String _computeExpression(final VariableUse exp) {
    return MathGenerator.computeBinding(exp.getRef());
  }
  
  protected static String _computeBinding(final VarBinding binding) {
    String _xblockexpression = null;
    {
      boolean _containsKey = MathGenerator.variables.containsKey(binding.getName());
      boolean _not = (!_containsKey);
      if (_not) {
        MathGenerator.computeExpression(binding);
      }
      _xblockexpression = MathGenerator.variables.get(binding.getName());
    }
    return _xblockexpression;
  }
  
  protected static String _computeBinding(final LetBinding binding) {
    return MathGenerator.computeExpression(binding.getBinding());
  }
  
  public static String computeExpression(final EObject exp) {
    if (exp instanceof Div) {
      return _computeExpression((Div)exp);
    } else if (exp instanceof LetBinding) {
      return _computeExpression((LetBinding)exp);
    } else if (exp instanceof MathNumber) {
      return _computeExpression((MathNumber)exp);
    } else if (exp instanceof Minus) {
      return _computeExpression((Minus)exp);
    } else if (exp instanceof Mult) {
      return _computeExpression((Mult)exp);
    } else if (exp instanceof Plus) {
      return _computeExpression((Plus)exp);
    } else if (exp instanceof VarBinding) {
      return _computeExpression((VarBinding)exp);
    } else if (exp instanceof VariableUse) {
      return _computeExpression((VariableUse)exp);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(exp).toString());
    }
  }
  
  public static String computeBinding(final Binding binding) {
    if (binding instanceof LetBinding) {
      return _computeBinding((LetBinding)binding);
    } else if (binding instanceof VarBinding) {
      return _computeBinding((VarBinding)binding);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(binding).toString());
    }
  }
}
