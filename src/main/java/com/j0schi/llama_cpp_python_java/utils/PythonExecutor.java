package com.j0schi.llama_cpp_python_java.utils;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Component;

@Component
public class PythonExecutor {

    public String executePythonCode(String question) {
        PythonInterpreter interpreter = new PythonInterpreter();

        // Запустите код Python из main.py
        String pythonCode = "from llama_cpp import Llama\n" +
                "print('Loading model...')\n" +
                "llm = Llama(model_path='/home/llama-cpp-user/model/vicuna-7b-v1.3-superhot-8k.ggmlv3.q5_K_M.bin')\n" +
                "print('Model loaded!')\n" +
                "stream = llm(\"" + question + "\", max_tokens=300, stop=['\\n', ' Q:'], echo=True)\n" +
                "result = ' '.join(stream)\n" +
                "result";

        interpreter.exec(pythonCode);

        // Получите результат выполнения кода Python
        String result = interpreter.get("result").toString();

        interpreter.close();

        return result;
    }
}



