package com.necleo.codemonkey.lib.FileImportMapperReact;

import java.util.HashSet;
import java.util.Set;

public class GenFileFunctions {

  public Set<String> imports = new HashSet<>();

  //    public void addToImports(String importsStatement){
  //        this.imports.add(importsStatement);
  //    }

  public String genFile(Set<String> handlerFunctions, String genCodeReturn) {
    String funcsInsideClass = "";
    String[] handlerFuncsArr = handlerFunctions.toArray(new String[handlerFunctions.size()]);
    for (String s : handlerFuncsArr) {
      funcsInsideClass += s;
    }
    String insideReturn = genCodeReturn;
    String topline = "";
    topline +=
        "import * as React from 'react';\nimport { BrowserRouter } from 'react-router-dom'\n\n";
    String lastline =
        """
                    </div>
                    </BrowserRouter>
                  );
                }""";
    String imports = "";
    String[] handleImportsArr = this.imports.toArray(new String[this.imports.size()]);
    for (String si : handleImportsArr) {
      imports += si;
    }

    String structure =
        topline
            + imports
            + "export function App(props) { \n\n"
            + funcsInsideClass
            + "\nreturn (\n"
            + "    <BrowserRouter>\n"
            + "    <div className='App'>"
            + insideReturn
            + lastline;
    return structure;
  }
}
