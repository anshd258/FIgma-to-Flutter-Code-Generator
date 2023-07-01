package com.necleo.codemonkey.service.flutter;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.PrimaryAxisAlignItems.SPACE_BETWEEN;

import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.*;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.utils.*;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AutoLayoutFrameCGI {
  @Lazy FlutterFigmaWidgetFactory flutterFigmaNodeFactory;

  public String generateAutoLayoutFrame(
      StringBuilder genCode,
      FigmaFrameNode figmaNode,
      FlutterWI flutterNecleoDataNode,
      FlutterGI flutterGI,
      FigmaNode parentFigmaNode) {

    String finalCode = "";
    LayoutMode layoutMode = figmaNode.getLayoutMode();
    LayoutWrap layoutWrap = figmaNode.getLayoutWrap();
    LayoutMode parentLayoutMode = parentLayoutMode(parentFigmaNode);
    String childrenOfAutoLayout = getChildren(figmaNode, flutterNecleoDataNode, flutterGI);
    // set Height and width of the container
    if ( figmaNode.getLayoutSizingHorizontal().equals(LayoutSizing.FIXED) ) {
      genCode.append(
              SizeUtil.getWidth(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
    }
    if (figmaNode.getLayoutSizingVertical().equals(LayoutSizing.FIXED)) {
      genCode.append(
              SizeUtil.getHeight(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
    }

    genCode.append("child: ");
    switch (layoutMode) {
      case VERTICAL -> genCode.append(
          Flex.of(Flex.Type.Column).wrap(childrenOfAutoLayout, figmaNode));
      case HORIZONTAL -> {
        switch (layoutWrap) {
          case WRAP -> genCode.append(
              Flex.of(Flex.Type.Wrap).wrap(childrenOfAutoLayout, figmaNode));
          case NO_WRAP -> genCode.append(
              Flex.of(Flex.Type.Row).wrap(childrenOfAutoLayout, figmaNode));
        }
      }
      case NONE -> genCode.append(
              Flex.of(Flex.Type.Stack).wrap(childrenOfAutoLayout, figmaNode));
    }
    genCode.append("),");

    // Wrapping to Fill or Hug
    switch (parentLayoutMode) {
      case VERTICAL -> {
        if( figmaNode.getLayoutSizingHorizontal().equals(LayoutSizing.HUG) ) {
          finalCode = wrapWithSingleParent("IntrinsicWidth", genCode);
        }
        if( figmaNode.getLayoutSizingVertical().equals(LayoutSizing.FILL) ) {
          finalCode = wrapWithSingleParent("Expanded", genCode);
        }
      }

      case HORIZONTAL -> {
        if( figmaNode.getLayoutSizingVertical().equals(LayoutSizing.HUG) ) {
          finalCode = wrapWithSingleParent("IntrinsicHeight", genCode);
        }
        if( figmaNode.getLayoutSizingHorizontal().equals(LayoutSizing.FILL) ) {
          finalCode = wrapWithSingleParent("Expanded", genCode);
        }
      }

      case NONE -> {
        finalCode = genCode.toString();
      }
    }

    return finalCode;
  }

  private String wrapWithSingleParent(String name, StringBuilder genCode) {
    return """
            
            %s(
              child: %s
            ),
            """
        .formatted(name, genCode);
  }

  private String getChildren(
      FigmaFrameNode figmaNode, FlutterWI flutterNecleoDataNode, FlutterGI flutterGI) {
    //      figmaNode.getChild().stream().reduce("",
    //              child -> mapper()
    //              , (x,y) -> String.join(",", x,y));

    if (figmaNode.getChild() != null) {
      StringBuilder childString = new StringBuilder("children: [\n");
      for (int i = 0; i < figmaNode.getChild().size(); i++) {
        String genChild = "";
        String gen = "";

        FigmaNodeMapper figmaNodeMapper =
                new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
        Optional<FlutterCGI> flutterCGIOptional =
                flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
        int childIndex = i;
        genChild +=
                flutterCGIOptional
                        .map(
                                flutterCGI ->
                                        flutterCGI.generate(
                                                figmaNode.getChild().get(childIndex),
                                                figmaNode,
                                                flutterGI,
                                                flutterNecleoDataNode))
                        .orElse("");

        if (!(figmaNode.getLayoutMode().equals(LayoutMode.NONE))) {
          if (i != figmaNode.getChild().size() - 1) {
            if (!(figmaNode.getPrimaryAxisAlignItems().equals(SPACE_BETWEEN))) {
              gen = SpacingUtil.getSpacing(figmaNode);
            }
          }
          childString.append(genChild);
          childString.append(gen);
          System.out.println(i + " -> " + childString);
        } else {
          gen = PositionUtil.getPosition(genChild, figmaNode.getChild().get(i), figmaNode, flutterNecleoDataNode);
          childString.append(gen);
          System.out.println(i + " -> " + childString);
        }
      }

      return childString + "],\n";
    }

    return "";
  }

  private LayoutMode parentLayoutMode(FigmaNode parentFigmaNode) {
    if (parentFigmaNode instanceof FigmaFrameNode pFigmaNode) {
      return switch (pFigmaNode.getLayoutMode()) {
        case VERTICAL -> LayoutMode.VERTICAL; // says parent is a column
        case HORIZONTAL -> LayoutMode.HORIZONTAL; // says parent is a row
        case NONE -> LayoutMode.NONE; // saya not column not a row
      };
    }

    return LayoutMode.NONE;
  }

  //  private String getWrap(
  //      StringBuilder genCode,
  //      FigmaFrameNode figmaNode,
  //      FlutterWI flutterNecleoDataNode,
  //      FlutterGI flutterGI,
  //      Map<String, Boolean> hugAndFillProperties) {
  //
  //    final String upperWrap = "Wrap(\n";
  //    final String lowerWrap = "],\n),\n";
  //
  //    genCode.append(upperWrap).append("direction: Axis.horizontal,");
  //    genCode.append(mainCrossAlignUtil.getWrapAlignment(figmaNode.getPrimaryAxisAlignItems()));
  //    genCode.append(mainCrossAlignUtil.getRunAlignment(figmaNode.getCounterAxisAlignItems()));
  //    genCode.append("\nspacing:
  // ").append(String.valueOf(figmaNode.getItemSpacing())).append(",");
  //    genCode
  //        .append("\nrunSpacing: ")
  //        .append(String.valueOf(figmaNode.getCounterAxisSpacing()))
  //        .append(",");
  //    genCode.append("children:[\n");
//      for (int i = 0; i < figmaNode.getChild().size(); i++) {
//        String genChild = "";
//        String gen = "";
//        FigmaNodeMapper figmaNodeMapper =
//            new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
//        Optional<FlutterCGI> flutterCGIOptional =
//            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
//        if (i == (figmaNode.getChild().size() - 1)) {
//
//          int finalI = i;
//          genChild +=
//              flutterCGIOptional
//                  .map(
//                      flutterCGI ->
//                          flutterCGI.generate(
//                              figmaNode.getChild().get(finalI),
//                              figmaNode,
//                              flutterGI,
//                              flutterNecleoDataNode))
//                  .orElse("");
//          genCode.append(genChild);
//        } else {
//
//          int finalI1 = i;
//          genChild +=
//              flutterCGIOptional
//                  .map(
//                      flutterCGI ->
//                          flutterCGI.generate(
//                              figmaNode.getChild().get(finalI1),
//                              figmaNode,
//                              flutterGI,
//                              flutterNecleoDataNode))
//                  .orElse("");
//          if (!(figmaNode.getPrimaryAxisAlignItems().equals(SPACE_BETWEEN))) {
//            gen += spacingUtil.getSpacing(figmaNode);
//          }
//
//          genCode.append(genChild);
//
//          genCode.append(gen);
//        }
//      }
  //    genCode.append(lowerWrap).append("\n").append("),\n");
  //
  //    if ((hugAndFillProperties.get("fillVertically") &&
  // hugAndFillProperties.get("fillHorizontally"))
  //        || (hugAndFillProperties.get("fillHorizontally")
  //            && !hugAndFillProperties.get("hugVertically")
  //            && !hugAndFillProperties.get("fillVertically"))) {
  //      wrapWithSingleParent("Expanded", genCode);
  //    } else if ((hugAndFillProperties.get("hugVertically")
  //            && hugAndFillProperties.get("hugHorizontally"))
  //        || (hugAndFillProperties.get("hugVertically")
  //            && !hugAndFillProperties.get("hugHorizontally")
  //            && !hugAndFillProperties.get("fillHorizontally"))) {
  //      wrapWithSingleParent("IntrinsicHeight", genCode);
  //    } else if ((hugAndFillProperties.get("hugVertically")
  //        && hugAndFillProperties.get("fillHorizontally"))) {
  //      ArrayList<String> parents = new ArrayList<>();
  //      parents.add("IntrinsicHeight");
  //      parents.add("Expanded");
  //      wrapWithMultipleParent(2, parents, genCode);
  //    }
  //
  //    return genCode.toString();
  //  }
  //
  //  private String getRow(
  //      StringBuilder genCode,
  //      FigmaFrameNode figmaNode,
  //      FlutterWI fultterNecleoDataNode,
  //      FlutterGI flutterGI,
  //      Map<String, Boolean> hugAndFillProperties) {
  //
  //    final String upperRow = "Row(\n";
  //    final String lowerRow = "),\n";
  //    genCode.append(upperRow);
  //
  // genCode.append(mainCrossAlignUtil.getMainAxisAlignment(figmaNode.getPrimaryAxisAlignItems()));
  //
  // genCode.append(mainCrossAlignUtil.getCrossAxisAlignment(figmaNode.getCounterAxisAlignItems()));
  //
  //    if (figmaNode.getChild() != null) {
  //      genCode.append("children:[\n");
  //      for (int i = 0; i < figmaNode.getChild().size(); i++) {
  //        String genChild = "";
  //        String gen = "";
  //        FigmaNodeMapper figmaNodeMapper =
  //            new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
  //        Optional<FlutterCGI> flutterCGIOptional =
  //            flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
  //        if (i == (figmaNode.getChild().size() - 1)) {
  //
  //          int finalI = i;
  //          genChild +=
  //              flutterCGIOptional
  //                  .map(
  //                      flutterCGI ->
  //                          flutterCGI.generate(
  //                              figmaNode.getChild().get(finalI),
  //                              figmaNode,
  //                              flutterGI,
  //                              fultterNecleoDataNode))
  //                  .orElse("");
  //          genCode.append(genChild);
  //        } else {
  //
  //          int finalI1 = i;
  //          genChild +=
  //              flutterCGIOptional
  //                  .map(
  //                      flutterCGI ->
  //                          flutterCGI.generate(
  //                              figmaNode.getChild().get(finalI1),
  //                              figmaNode,
  //                              flutterGI,
  //                              fultterNecleoDataNode))
  //                  .orElse("");
  //          if (!(figmaNode.getPrimaryAxisAlignItems().equals(SPACE_BETWEEN))) {
  //            gen += spacingUtil.getSpacing(figmaNode);
  //          }
  //
  //          genCode.append(genChild);
  //
  //          genCode.append(gen);
  //        }
  //      }
  //      genCode.append("],\n");
  //    }
  //    genCode.append(lowerRow);
  //    genCode.append("\n").append("),\n");
  //
  //    if ((hugAndFillProperties.get("fillVertically") &&
  // hugAndFillProperties.get("fillHorizontally"))
  //        || (hugAndFillProperties.get("fillHorizontally")
  //            && !hugAndFillProperties.get("hugVertically")
  //            && !hugAndFillProperties.get("fillVertically"))) {
  //      wrapWithSingleParent("Expanded", genCode);
  //    } else if ((hugAndFillProperties.get("hugVertically")
  //            && hugAndFillProperties.get("hugHorizontally"))
  //        || (hugAndFillProperties.get("hugVertically")
  //            && !hugAndFillProperties.get("hugHorizontally")
  //            && !hugAndFillProperties.get("fillHorizontally"))) {
  //      wrapWithSingleParent("IntrinsicHeight", genCode);
  //    } else if ((hugAndFillProperties.get("hugVertically")
  //        && hugAndFillProperties.get("fillHorizontally"))) {
  //      ArrayList<String> parents = new ArrayList<>();
  //      parents.add("IntrinsicHeight");
  //      parents.add("Expanded");
  //      wrapWithMultipleParent(2, parents, genCode);
  //    }
  //
  //    return genCode.toString();
  //  }
  //
  //  private String getColumn(
  //      StringBuilder genCode,
  //      FigmaFrameNode figmaNode,
  //      FlutterWI fultterNecleoDataNode,
  //      FlutterGI flutterGI,
  //      Map<String, Boolean> hugAndFillProperties) {
  //
  //    final String upperColumn = "Column(\n";
  //    final String lowerColumn = "),\n";
  //    genCode.append(upperColumn);
  //
  // genCode.append(mainCrossAlignUtil.getMainAxisAlignment(figmaNode.getPrimaryAxisAlignItems()));
  //
  // genCode.append(mainCrossAlignUtil.getCrossAxisAlignment(figmaNode.getCounterAxisAlignItems()));
  //    if (figmaNode.getChild() != null) {
  //      genCode.append("children:[\n");
//        for (int i = 0; i <= figmaNode.getChild().toArray().length - 1; i++) {
//          String genChild = "";
//          String gen = "";
//
//          FigmaNodeMapper figmaNodeMapper =
//              new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
//          Optional<FlutterCGI> flutterCGIOptional =
//              flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
//          if (i < figmaNode.getChild().toArray().length - 1) {
//
//            int finalI = i;
//            genChild +=
//                flutterCGIOptional
//                    .map(
//                        flutterCGI ->
//                            flutterCGI.generate(
//                                figmaNode.getChild().get(finalI),
//                                figmaNode,
//                                flutterGI,
//                                fultterNecleoDataNode))
//                    .orElse("");
//            gen =
//                positionUtil.getPosition(
//                    genChild, figmaNode.getChild().get(i), figmaNode, fultterNecleoDataNode);
//            genCode.append(gen);
//          } else {
//
//            int finalI1 = i;
//            genChild +=
//                flutterCGIOptional
//                    .map(
//                        flutterCGI ->
//                            flutterCGI.generate(
//                                figmaNode.getChild().get(finalI),
//                                figmaNode,
//                                flutterGI,
//                                fultterNecleoDataNode))
//                    .orElse("");
//            gen =
//                positionUtil.getPosition(
//                    genChild, figmaNode.getChild().get(i), figmaNode, fultterNecleoDataNode);
//            genCode.append(gen);
//          }
//        }
//        genCode.append("],\n");
//      }
  //    genCode.append(lowerColumn);
  //    genCode.append("\n").append("),\n");
  //
  //    if ((hugAndFillProperties.get("fillVertically") &&
  // hugAndFillProperties.get("fillHorizontally"))
  //        || (hugAndFillProperties.get("fillVertically")
  //            && !hugAndFillProperties.get("hugHorizontally")
  //            && !hugAndFillProperties.get("fillHorizontally"))) {
  //      wrapWithSingleParent("Expanded", genCode);
  //    } else if ((hugAndFillProperties.get("hugVertically")
  //            && hugAndFillProperties.get("hugHorizontally"))
  //        || (hugAndFillProperties.get("hugHorizontally")
  //            && !hugAndFillProperties.get("hugVertically")
  //            && !hugAndFillProperties.get("fillVertically"))) {
  //      wrapWithSingleParent("IntrinsicWidth", genCode);
  //    } else if ((hugAndFillProperties.get("fillVertically")
  //        && hugAndFillProperties.get("hugHorizontally"))) {
  //      ArrayList<String> parents = new ArrayList<>();
  //      parents.add("IntrinsicWidth");
  //      parents.add("Expanded");
  //      wrapWithMultipleParent(2, parents, genCode);
  //    }
  //    return genCode.toString();
  //  }
  //
  //  private String getContainerWidget(
  //      StringBuilder genCode,
  //      FigmaFrameNode figmaNode,
  //      FlutterWI fultterNecleoDataNode,
  //      FlutterGI flutterGI) {
  //
  //    final String upperStack = "Stack(\n";
  //    final String lowerStack = "),";
  //    String center = "";
  //    genCode.append(upperStack).append(center);
  //    genCode.append(" alignment: Alignment.center,");
  //    if (figmaNode.getChild() != null) {
  //      genCode.append("children:[\n");
//        for (int i = 0; i < figmaNode.getChild().size(); i++) {
//          String genChild = "";
//          String gen;
//
//          FigmaNodeMapper figmaNodeMapper =
//              new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
//          Optional<FlutterCGI> flutterCGIOptional =
//              flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
//          int finalI = i;
//          genChild +=
//              flutterCGIOptional
//                  .map(
//                      flutterCGI ->
//                          flutterCGI.generate(
//                              figmaNode.getChild().get(finalI),
//                              figmaNode,
//                              flutterGI,
//                              fultterNecleoDataNode))
//                  .orElse("");
//          gen =
//              positionUtil.getPosition(
//                  genChild, figmaNode.getChild().get(i), figmaNode, fultterNecleoDataNode);
//          genCode.append(gen);
//        }
  //      genCode.append("],\n");
  //    }
  //    genCode.append(lowerStack);
  //    genCode.append("\n").append("),\n");
  //    return genCode.toString();
  //  }
}
