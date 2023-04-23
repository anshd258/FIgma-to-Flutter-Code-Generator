package com.necleo.codemonkey.lib.FileImportMapperReact;

import java.util.Set;

public class GenFileFunctions {

    public String genFile(Set<String> ImportsFunctions, String genCodeReturn){
        String funcsInsideClass = "";
        String[] ImportFuncsArr = ImportsFunctions.toArray(new String[ImportsFunctions.size()]);
        for (String s : ImportFuncsArr) {
            funcsInsideClass += s;
        }
        String insideReturn = genCodeReturn;
        String topline = "";
        topline +="import * as React from 'react';\nimport { BrowserRouter } from 'react-router-dom'\n\n";
        String lastline = """
                    </div>
                    </BrowserRouter>
                  );
                }""";
        String structure = topline + "export function App(props) { \n\n" + funcsInsideClass + "\nreturn (\n" +
                "    <BrowserRouter>\n" +
                "    <div className='App'>" +
                insideReturn +
                lastline
                ;
        return structure;
    }

}
