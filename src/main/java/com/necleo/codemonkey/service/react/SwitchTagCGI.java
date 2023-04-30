package com.necleo.codemonkey.service.react;

import com.necleo.codemonkey.lib.FileImportMapperReact.GenFileFunctions;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.FigmaNodeTypes;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.nodeTypes.TagDataType;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SwitchTagCGI implements ReactCGI {

  GenFileFunctions genFileFunctions = new GenFileFunctions();

  @Override
  public String generate(
      FigmaNode figmaNode,
      FigmaNode node,
      Map<String, TagData> tagDataMap,
      Set<String> importsFunctions) {

    return generat(figmaNode, importsFunctions);
  }

  private String generat(FigmaNode figmaNode, Set<String> importsFunctions) {
    String genCode = "";
    final String importState = "import ReactSwitch from 'react-switch';";
    genFileFunctions.imports.add(importState);
    String handlerFunc =
        """
                const [checked, setChecked] = useState(true);

                  const handleChange = val => {
                    setChecked(val)
                  }""";
    importsFunctions.add(handlerFunc);
    genCode = getSwitchStyles(figmaNode);
    return genCode;
  }

  public String getSwitchStyles(FigmaNode fNode) {
    String switchCode = "";
    switchCode += """
                        <ReactSwitch\s""";
    switchCode += getOtherProps(fNode);

    switchCode +=
        """
                        checked={checked}
                        onChange={handleChange}
                        />
                      """;
    return switchCode;
  }

  private String getOtherProps(FigmaNode fNode) {
    String prpos = "";
    prpos += "onColor=\"#86d3ff\"\n";
    //                            onHandleColor="#2693e6"
    //                            handleDiameter={30}
    //                            uncheckedIcon={false}
    //                            checkedIcon={false}
    //                            boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
    //                            activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
    //                            height={20}
    //                            width={48}"";
    prpos += "handleDiameter={30}\n";
    prpos +=
        """
                uncheckedIcon={false}
                checkedIcon={false}
                boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
                activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
                """;
    prpos += "width={" + fNode.getHeight() + "}\n";
    prpos += "height={" + fNode.getHeight() + "}\n";
    return prpos;
  }

  @Override
  public Set<FigmaNodeMapper> getStrategy() {
    return Set.of(new FigmaNodeMapper(FigmaNodeTypes.RECTANGLE, TagDataType.SWITCH));
  }
}
