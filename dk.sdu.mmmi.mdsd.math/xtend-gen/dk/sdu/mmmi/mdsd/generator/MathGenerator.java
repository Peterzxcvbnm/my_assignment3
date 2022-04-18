/**
 * generated by Xtext 2.25.0
 */
package dk.sdu.mmmi.mdsd.generator;

import com.google.common.collect.Iterators;
import dk.sdu.mmmi.mdsd.math.Binding;
import dk.sdu.mmmi.mdsd.math.Div;
import dk.sdu.mmmi.mdsd.math.Expression;
import dk.sdu.mmmi.mdsd.math.External;
import dk.sdu.mmmi.mdsd.math.ExternalUse;
import dk.sdu.mmmi.mdsd.math.LetBinding;
import dk.sdu.mmmi.mdsd.math.MathExp;
import dk.sdu.mmmi.mdsd.math.MathNumber;
import dk.sdu.mmmi.mdsd.math.Minus;
import dk.sdu.mmmi.mdsd.math.Mult;
import dk.sdu.mmmi.mdsd.math.Parenthesis;
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
    _builder.append("math_expression/");
    String _name = className.getName();
    _builder.append(_name);
    _builder.append(".java");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package math_expression;");
    _builder_1.newLine();
    _builder_1.append("public class ");
    String _name_1 = className.getName();
    _builder_1.append(_name_1);
    _builder_1.append(" {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    {
      for(final VarBinding variable : variables) {
        _builder_1.append("public int ");
        String _name_2 = variable.getName();
        _builder_1.append(_name_2);
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
      }
    }
    _builder_1.newLine();
    {
      int _size = math.getExternals().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder_1.append("private External external;");
        _builder_1.newLine();
        _builder_1.newLine();
        _builder_1.append("public ");
        String _name_3 = className.getName();
        _builder_1.append(_name_3);
        _builder_1.append("(External external) {");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("  ");
        _builder_1.append("this.external = external;");
        _builder_1.newLine();
        _builder_1.append("}");
        _builder_1.newLine();
      }
    }
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public void compute() {");
    _builder_1.newLine();
    {
      boolean _hasElements = false;
      for(final VarBinding variable_1 : variables) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder_1.appendImmediate("\r\n\t\t\t", "");
        }
        String _name_4 = variable_1.getName();
        _builder_1.append(_name_4);
        _builder_1.append(" = ");
        String _computeExpression = MathGenerator.computeExpression(variable_1);
        _builder_1.append(_computeExpression);
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
      }
    }
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.newLine();
    {
      int _size_1 = math.getExternals().size();
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        _builder_1.append("public interface External {");
        _builder_1.newLine();
        {
          EList<External> _externals = math.getExternals();
          for(final External external : _externals) {
            _builder_1.append("public int ");
            String _name_5 = external.getName();
            _builder_1.append(_name_5);
            _builder_1.append("(");
            {
              EList<ParmeterTypes> _parameters = external.getParameters();
              boolean _hasElements_1 = false;
              for(final ParmeterTypes param : _parameters) {
                if (!_hasElements_1) {
                  _hasElements_1 = true;
                } else {
                  _builder_1.appendImmediate(", ", "");
                }
                _builder_1.append(param);
                _builder_1.append(" n");
                int _plusPlus = counter++;
                _builder_1.append(_plusPlus);
              }
            }
            _builder_1.append(");");
            _builder_1.newLineIfNotEmpty();
          }
        }
        _builder_1.append("}");
        _builder_1.newLine();
      }
    }
    _builder_1.append("}");
    _builder_1.newLine();
    fsa.generateFile(_builder.toString(), _builder_1);
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
    String _computeExpression = MathGenerator.computeExpression(exp.getBody());
    String _plus = ("(" + _computeExpression);
    return (_plus + ")");
  }
  
  protected static String _computeExpression(final VariableUse exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getRef());
    String _plus = ("(" + _computeExpression);
    return (_plus + ")");
  }
  
  protected static String _computeExpression(final Parenthesis exp) {
    String _computeExpression = MathGenerator.computeExpression(exp.getExpression());
    String _plus = ("( " + _computeExpression);
    return (_plus + " )");
  }
  
  protected static String _computeExpression(final ExternalUse exp) {
    int _size = exp.getExpressions().size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("this.external.");
      String _name = exp.getRef().getName();
      _builder.append(_name);
      _builder.append("(");
      {
        EList<Expression> _expressions = exp.getExpressions();
        boolean _hasElements = false;
        for(final Expression expression : _expressions) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(", ", "");
          }
          String _computeExpression = MathGenerator.computeExpression(expression);
          _builder.append(_computeExpression);
        }
      }
      _builder.append(")");
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("this.external.");
      String _name_1 = exp.getRef().getName();
      _builder_1.append(_name_1);
      _builder_1.append("()");
      return _builder_1.toString();
    }
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
    String _computeExpression = MathGenerator.computeExpression(binding.getBinding());
    String _plus = ("(" + _computeExpression);
    return (_plus + ")");
  }
  
  public static String computeExpression(final EObject exp) {
    if (exp instanceof Div) {
      return _computeExpression((Div)exp);
    } else if (exp instanceof ExternalUse) {
      return _computeExpression((ExternalUse)exp);
    } else if (exp instanceof LetBinding) {
      return _computeExpression((LetBinding)exp);
    } else if (exp instanceof MathNumber) {
      return _computeExpression((MathNumber)exp);
    } else if (exp instanceof Minus) {
      return _computeExpression((Minus)exp);
    } else if (exp instanceof Mult) {
      return _computeExpression((Mult)exp);
    } else if (exp instanceof Parenthesis) {
      return _computeExpression((Parenthesis)exp);
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
