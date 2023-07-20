package com.j0schi.llama_cpp_python_java.utils;

import org.python.core.*;
import org.python.util.PythonInterpreter;

public class PythonExecutor {

    private PyObject llamaModule;

    public PyObject llamaInit() {

        PythonInterpreter interpreter = new PythonInterpreter();

        // Добавляем путь к директории с модулем llama.py в sys.path
        String pythonCode = "import sys\n"
                + "sys.path.append('/home/llama-cpp-user/server/src')"; // Замените /path/to/directory на путь к директории с llama.py

        interpreter.exec(pythonCode);

        // Загружаем модуль llama и получаем его объект
        pythonCode = "from llama import Llama\n"
                + "llama = Llama('/home/llama-cpp-user/model/vicuna-7b-v1.3-superhot-8k.ggmlv3.q5_K_M.bin')"; // Замените /path/to/model на путь к модели
        interpreter.exec(pythonCode);

        // Получаем объект модуля llama и сохраняем его
        PyObject llamaModule = interpreter.get("llama");

        interpreter.close();

        return llamaModule;
    }

    public String executeQuestion(String question) {
        // Проверяем, что объект модуля llama был инициализирован
        if (llamaModule == null) {
            llamaModule = llamaInit();
        }

        // Получаем объект функции llm из модуля llama
        PyObject llmFunction = llamaModule.__findattr__("llm");

        // Вызываем функцию llm с указанными аргументами
        PyObject pyResponse = llmFunction.__call__(new PyUnicode(question), new PyLong(300), new PyTuple(new PyList(), new PyList()), Py.True);

        // Конвертируем результат в строку и возвращаем его
        String response = pyResponse.toString();
        return response;
    }
}
