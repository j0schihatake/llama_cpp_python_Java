package com.j0schi.llama_cpp_python_java.utils;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.Arrays;

public class PythonExecutor {

    public String runLlm(String message, int maxTokens, String[] stop, boolean echo) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from llm_api import llm");

        PyObject llmFunction = interpreter.get("llm");
        if (llmFunction != null && llmFunction.isCallable()) {
            // Создаем объекты PyObjects для аргументов
            PyObject args = new PyTuple(
                    new PyString(message),
                    new PyInteger(maxTokens),
                    new PyList(Arrays.asList(stop)),
                    Py.java2py(echo)
            );

            PyObject result = llmFunction.__call__(args);

            if (result != null) {
                return result.toString();
            }
        }

        return "Error: Failed to execute llm()";
    }
}

