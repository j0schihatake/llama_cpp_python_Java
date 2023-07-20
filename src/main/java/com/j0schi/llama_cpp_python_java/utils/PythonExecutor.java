package com.j0schi.llama_cpp_python_java.utils;

import jakarta.annotation.PostConstruct;
import org.python.core.*;
import org.python.util.PythonInterpreter;

public class PythonExecutor {

    private PyObject llm;

    @PostConstruct
    public void llamaInit() {
        PythonInterpreter interpreter = new PythonInterpreter();

        // Добавляем путь к директории с модулем llama_cpp в sys.path
        String pythonCode = "import sys\n"
                + "sys.path.append('~/llama.cpp')"; // Замените /path/to/llama_cpp на реальный путь

        interpreter.exec(pythonCode);

        // Загружаем Python-модуль llama_cpp и получаем Python-объект с функцией llm
        pythonCode = "from llama_cpp import llm";
        interpreter.exec(pythonCode);

        // Получаем Python-функцию llm
        PyObject llmFunction = interpreter.get("llm");

        // Сохраняем Python-функцию llm в поле llm
        this.llm = llmFunction;

        interpreter.close();
    }

    public String executeQuestion(String question) {
        // Проверяем, что Python-функция llm была инициализирована
        if (llm == null) {
            throw new IllegalStateException("Python function llm is not initialized. Call llamaInit() first.");
        }

        // Вызываем Python-функцию llm для выполнения вопроса
        PyObject pyResponse = llm.__call__(new PyString(question), new PyInteger(300), new PyTuple(new PyString("\n"), new PyString(" Q:")), Py.True);

        // Конвертируем результат в строку и возвращаем его
        String response = pyResponse.toString();
        return response;
    }
}



