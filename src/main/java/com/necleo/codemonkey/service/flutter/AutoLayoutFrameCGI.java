package com.necleo.codemonkey.service.flutter;

import com.necleo.codemonkey.factory.FlutterFigmaWidgetFactory;
import com.necleo.codemonkey.flutter.index.FlutterGI;
import com.necleo.codemonkey.lib.types.FigmaNode;
import com.necleo.codemonkey.lib.types.TagData;
import com.necleo.codemonkey.lib.types.enums.figmaEnums.*;
import com.necleo.codemonkey.lib.types.figma.FigmaFrameNode;
import com.necleo.codemonkey.model.factory.FigmaNodeMapper;
import com.necleo.codemonkey.model.factory.FlutterWI;
import com.necleo.codemonkey.service.flutter.utils.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.necleo.codemonkey.lib.types.enums.figmaEnums.PrimaryAxisAlignItems.SPACE_BETWEEN;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AutoLayoutFrameCGI {

    SizeUtil sizeUtil = new SizeUtil();
    PositionUtil positionUtil = new PositionUtil();
    FlexibleUtil flexibleUtil = new FlexibleUtil();
    SpacingUtil spacingUtil = new SpacingUtil();
    MainCrossAlignUtil mainCrossAlignUtil = new MainCrossAlignUtil();
    @Lazy
    FlutterFigmaWidgetFactory flutterFigmaNodeFactory;


    public String generateAutoLayoutFrame(
            StringBuilder genCode,
            FigmaFrameNode figmaNode,
            FlutterWI flutterNecleoDataNode,
            TagData tagData,
            FlutterGI flutterGI,
            FigmaNode parentFigmaNode) {

        LayoutAlign layoutAlign = figmaNode.getLayoutAlign();
        int layoutGrow = figmaNode.getLayoutGrow();
        LayoutMode layoutMode = figmaNode.getLayoutMode();
        LayoutWrap layoutWrap = figmaNode.getLayoutWrap();
        CounterAxisSizingMode counterAxisSizingMode = figmaNode.getCounterAxisSizingMode();
        PrimaryAxisSizingMode primaryAxisSizingMode = figmaNode.getPrimaryAxisSizingMode();
        Map<String, Boolean> hugAndFillProperties = new HashMap<String, Boolean>();
        hugAndFillProperties.put("fillVertically", false);
        hugAndFillProperties.put("hugVertically", false);
        hugAndFillProperties.put("fillHorizontally", false);
        hugAndFillProperties.put("hugHorizontally", false);

        // Setting Width and crossAxisSizing
        switch (layoutMode) {
            case VERTICAL -> {
                switch (layoutAlign) {
                    case STRETCH -> hugAndFillProperties.put("fillHorizontally", true);
                    case INHERIT -> {
                        switch (counterAxisSizingMode) {
                            case FIXED ->
                                    genCode.append(sizeUtil.getWidth(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
                            case AUTO -> hugAndFillProperties.put("hugHorizontally", true);
                        };
                    }
                }
            }

            case HORIZONTAL -> {
                switch (layoutAlign) {
                    case STRETCH -> hugAndFillProperties.put("fillVertically", true);
                    case INHERIT -> {
                        switch (counterAxisSizingMode) {
                            case FIXED ->
                                    genCode.append(sizeUtil.getHeight(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
                            case AUTO -> hugAndFillProperties.put("hugVertically", true);
                        };
                    }
                }
            }

            case NONE -> {break;}
        };

        // Setting Height and primaryAxisSizing
        switch (layoutMode) {
            case VERTICAL -> {
                switch (layoutGrow) {
                    case 1 -> hugAndFillProperties.put("fillVertically", true);
                    case 0 -> {
                        switch (primaryAxisSizingMode) {
                            case FIXED ->
                                    genCode.append(sizeUtil.getHeight(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
                            case AUTO -> hugAndFillProperties.put("hugVertically", true);
                        }
                    }
                    default -> {
                        break;
                    }
                }
            }

            case HORIZONTAL -> {
                switch (layoutGrow) {
                    case 1 -> hugAndFillProperties.put("fillHorizontally", true);
                    case 0 -> {
                        switch (primaryAxisSizingMode) {
                            case FIXED ->
                                    genCode.append(sizeUtil.getWidth(figmaNode, flutterNecleoDataNode.getMainScreen(), flutterGI));
                            case AUTO -> hugAndFillProperties.put("hugHorizontally", true);
                        }
                    }
                    default -> {
                        break;
                    }
                }
            }

            case NONE -> {break;}
        };

        genCode.append("child: ");
        switch (layoutMode) {
            case VERTICAL -> genCode.append(
                    getColumn(genCode, figmaNode, tagData, flutterNecleoDataNode, flutterGI, hugAndFillProperties));
            case HORIZONTAL -> {
                switch (layoutWrap) {
                    case WRAP -> genCode.append(getWrap(genCode, figmaNode, tagData, flutterNecleoDataNode, flutterGI, hugAndFillProperties));
                    case NO_WRAP -> genCode.append(getRow(genCode, figmaNode, tagData, flutterNecleoDataNode, flutterGI, hugAndFillProperties));
                }
                ;
            }
            case NONE -> genCode.append(getContainerWidget(genCode, figmaNode, tagData, flutterNecleoDataNode, flutterGI));
        };

        return genCode.toString();
    }

    private StringBuilder wrapWithSingleParent( String name, StringBuilder genCode ) {
        StringBuilder ans = new StringBuilder();
        ans.append("\n")
                .append(name).append("(\n")
                .append(genCode)
                .append("),\n");
        return ans;
    }

    private StringBuilder wrapWithMultipleParent(int parentNumber, ArrayList<String> name, StringBuilder genCode ) {
        for( int i = 0; i < parentNumber; i++ ) {
            StringBuilder ans = new StringBuilder();
            ans.append("\n")
                    .append(name.get(i)).append("(\n")
                    .append(genCode)
                    .append("),\n");

            genCode = ans;
        }
        return genCode;
    }

    private StringBuilder wrapWithSingleColumnOrRow( String name, StringBuilder genCode ) {
        StringBuilder ans = new StringBuilder();
        ans.append("\n")
                .append(name)
                .append("(\nchildren: [\n")
                .append(genCode)
                .append("],\n),\n");
        return ans;
    }

    private String isParentAColumn( FigmaNode parentFigmaNode ) {
        if (parentFigmaNode instanceof FigmaFrameNode pFigmaNode) {
            return switch (pFigmaNode.getLayoutMode()) {
                case VERTICAL -> "Column"; // says parent is a column
                case HORIZONTAL -> "Row"; // says parent is a row
                case NONE -> "None"; // saya not column not a row
            };
        }

        return "None";
    }

    private String getWrap(
            StringBuilder genCode,
            FigmaFrameNode figmaNode,
            TagData tagData,
            FlutterWI flutterNecleoDataNode,
            FlutterGI flutterGI,
            Map<String, Boolean> hugAndFillProperties) {

        final String upperWrap = "Wrap(\n";
        final String lowerWrap = "],\n),\n";

        genCode.append(upperWrap).append("direction: Axis.horizontal,");
        genCode.append(
                mainCrossAlignUtil.getWrapAlignment(
                        figmaNode.getPrimaryAxisAlignItems()));
        genCode.append(
                mainCrossAlignUtil.getRunAlignment(
                        figmaNode.getCounterAxisAlignItems()));
        genCode.append("\nspacing: ").append(String.valueOf(figmaNode.getItemSpacing())).append(",");
        genCode.append("\nrunSpacing: ").append(String.valueOf(figmaNode.getCounterAxisSpacing())).append(",");
        genCode.append("children:[\n");
        for (int i = 0; i < figmaNode.getChild().size(); i++) {
            String genChild = "";
            String gen = "";
            FigmaNodeMapper figmaNodeMapper =
                    new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
            Optional<FlutterCGI> flutterCGIOptional =
                    flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
            if (i == (figmaNode.getChild().size() - 1)) {

                int finalI = i;
                genChild +=
                        flutterCGIOptional
                                .map(
                                        flutterCGI ->
                                                flutterCGI.generate(
                                                        figmaNode.getChild().get(finalI),
                                                        figmaNode,
                                                        flutterGI,
                                                        flutterNecleoDataNode))
                                .orElse("");
                genCode.append(genChild);
            } else {

                int finalI1 = i;
                genChild +=
                        flutterCGIOptional
                                .map(
                                        flutterCGI ->
                                                flutterCGI.generate(
                                                        figmaNode.getChild().get(finalI1),
                                                        figmaNode,
                                                        flutterGI,
                                                        flutterNecleoDataNode))
                                .orElse("");
                if (!(figmaNode.getPrimaryAxisAlignItems().equals(SPACE_BETWEEN))) {
                    gen += spacingUtil.getSpacing(figmaNode);
                }

                genCode.append(genChild);

                genCode.append(gen);
            }
        }
        genCode.append(lowerWrap).append("\n").append("),\n");

        if(
                (hugAndFillProperties.get("fillVertically") && hugAndFillProperties.get("fillHorizontally"))
                        || (hugAndFillProperties.get("fillHorizontally") && !hugAndFillProperties.get("hugVertically") && !hugAndFillProperties.get("fillVertically"))
        ) {
            wrapWithSingleParent( "Expanded", genCode );
        } else if(
                (hugAndFillProperties.get("hugVertically") && hugAndFillProperties.get("hugHorizontally"))
                        || (hugAndFillProperties.get("hugVertically") && !hugAndFillProperties.get("hugHorizontally") && !hugAndFillProperties.get("fillHorizontally"))
        ) {
            wrapWithSingleParent( "IntrinsicHeight", genCode );
        } else if(
                (hugAndFillProperties.get("hugVertically") && hugAndFillProperties.get("fillHorizontally"))
        ) {
            ArrayList<String> parents = new ArrayList<>();
            parents.add("IntrinsicHeight");
            parents.add("Expanded");
            wrapWithMultipleParent(2, parents, genCode );
        }

        return genCode.toString();
    }

    private String getRow(
            StringBuilder genCode,
            FigmaFrameNode figmaNode,
            TagData tagData,
            FlutterWI fultterNecleoDataNode,
            FlutterGI flutterGI,
            Map<String, Boolean> hugAndFillProperties) {

        final String upperRow = "Row(\n";
        final String lowerRow = "),\n";
        genCode.append(upperRow);
        genCode.append(
                mainCrossAlignUtil.getMainAxisAlignment(
                        figmaNode.getPrimaryAxisAlignItems()));
        genCode.append(
                mainCrossAlignUtil.getCrossAxisAlignment(
                        figmaNode.getCounterAxisAlignItems()));

        if( figmaNode.getChild() != null ) {
            genCode.append("children:[\n");
            for (int i = 0; i < figmaNode.getChild().size(); i++) {
                String genChild = "";
                String gen = "";
                FigmaNodeMapper figmaNodeMapper =
                        new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
                Optional<FlutterCGI> flutterCGIOptional =
                        flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
                if (i == (figmaNode.getChild().size() - 1)) {

                    int finalI = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    genCode.append(genChild);
                } else {

                    int finalI1 = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI1),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    if (!(figmaNode.getPrimaryAxisAlignItems().equals(SPACE_BETWEEN))) {
                        gen += spacingUtil.getSpacing(figmaNode);
                    }

                    genCode.append(genChild);

                    genCode.append(gen);
                }
            }
            genCode.append("],\n");
        }
        genCode.append(lowerRow);
        genCode.append("\n")
                .append("),\n");

        if(
                (hugAndFillProperties.get("fillVertically") && hugAndFillProperties.get("fillHorizontally"))
                || (hugAndFillProperties.get("fillHorizontally") && !hugAndFillProperties.get("hugVertically") && !hugAndFillProperties.get("fillVertically"))
        ) {
            wrapWithSingleParent( "Expanded", genCode );
        } else if(
                (hugAndFillProperties.get("hugVertically") && hugAndFillProperties.get("hugHorizontally"))
                || (hugAndFillProperties.get("hugVertically") && !hugAndFillProperties.get("hugHorizontally") && !hugAndFillProperties.get("fillHorizontally"))
        ) {
            wrapWithSingleParent( "IntrinsicHeight", genCode );
        } else if(
                (hugAndFillProperties.get("hugVertically") && hugAndFillProperties.get("fillHorizontally"))
        ) {
            ArrayList<String> parents = new ArrayList<>();
            parents.add("IntrinsicHeight");
            parents.add("Expanded");
            wrapWithMultipleParent(2, parents, genCode );
        }

        return genCode.toString();
    }

    private String getColumn(
            StringBuilder genCode,
            FigmaFrameNode figmaNode,
            TagData tagData,
            FlutterWI fultterNecleoDataNode,
            FlutterGI flutterGI,
            Map<String, Boolean> hugAndFillProperties) {

        final String upperColumn = "Column(\n";
        final String lowerColumn = "),\n";
        genCode.append(upperColumn);
        genCode.append(
                mainCrossAlignUtil.getMainAxisAlignment(figmaNode.getPrimaryAxisAlignItems()));
        genCode.append(
                mainCrossAlignUtil.getCrossAxisAlignment(
                        figmaNode.getCounterAxisAlignItems()));
        if( figmaNode.getChild() != null ) {
            genCode.append("children:[\n");
            for (int i = 0; i <= figmaNode.getChild().toArray().length - 1; i++) {
                String genChild = "";
                String gen = "";

                FigmaNodeMapper figmaNodeMapper =
                        new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
                Optional<FlutterCGI> flutterCGIOptional =
                        flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
                if (i < figmaNode.getChild().toArray().length - 1) {

                    int finalI = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    gen = positionUtil.getPosition(genChild, figmaNode.getChild().get(i), figmaNode,fultterNecleoDataNode);
                    genCode.append(gen);
                } else {

                    int finalI1 = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI1),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    gen = positionUtil.getPosition(genChild, figmaNode.getChild().get(i), figmaNode, fultterNecleoDataNode);
                    genCode.append(gen);
                }
            }
            genCode.append("],\n");
        }
        genCode.append(lowerColumn);
        genCode.append("\n")
                .append("),\n");

        if(
                (hugAndFillProperties.get("fillVertically") && hugAndFillProperties.get("fillHorizontally"))
                        || (hugAndFillProperties.get("fillVertically") && !hugAndFillProperties.get("hugHorizontally") && !hugAndFillProperties.get("fillHorizontally"))
        ) {
            wrapWithSingleParent( "Expanded", genCode );
        } else if(
                (hugAndFillProperties.get("hugVertically") && hugAndFillProperties.get("hugHorizontally"))
                        || (hugAndFillProperties.get("hugHorizontally") && !hugAndFillProperties.get("hugVertically") && !hugAndFillProperties.get("fillVertically"))
        ) {
            wrapWithSingleParent( "IntrinsicWidth", genCode );
        } else if(
                (hugAndFillProperties.get("fillVertically") && hugAndFillProperties.get("hugHorizontally"))
        ) {
            ArrayList<String> parents = new ArrayList<>();
            parents.add("IntrinsicWidth");
            parents.add("Expanded");
            wrapWithMultipleParent(2, parents, genCode );
        }

        return genCode.toString();
    }

    private String getContainerWidget(
            StringBuilder genCode,
            FigmaFrameNode figmaNode,
            TagData tagData,
            FlutterWI fultterNecleoDataNode,
            FlutterGI flutterGI) {

        final String upperStack = "Stack(\n";
        final String lowerStack = "),";
        String center = "";
        genCode.append(upperStack).append(center);
        genCode.append(" alignment: Alignment.center,");
        if( figmaNode.getChild() != null ) {
            genCode.append("children:[\n");
            for (int i = 0; i <= figmaNode.getChild().toArray().length - 1; i++) {
                String genChild = "";
                String gen = "";

                FigmaNodeMapper figmaNodeMapper =
                        new FigmaNodeMapper(figmaNode.getChild().get(i).getType(), null);
                Optional<FlutterCGI> flutterCGIOptional =
                        flutterFigmaNodeFactory.getProcessor(figmaNodeMapper);
                if (i < figmaNode.getChild().toArray().length - 1) {

                    int finalI = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    gen = positionUtil.getPosition(genChild, figmaNode.getChild().get(i), figmaNode,fultterNecleoDataNode);
                    genCode.append(gen);
                } else {

                    int finalI1 = i;
                    genChild +=
                            flutterCGIOptional
                                    .map(
                                            flutterCGI ->
                                                    flutterCGI.generate(
                                                            figmaNode.getChild().get(finalI1),
                                                            figmaNode,
                                                            flutterGI,
                                                            fultterNecleoDataNode))
                                    .orElse("");
                    gen = positionUtil.getPosition(genChild, figmaNode.getChild().get(i), figmaNode, fultterNecleoDataNode);
                    genCode.append(gen);
                }
            }
            genCode.append("],\n");
        }
        genCode.append(lowerStack);
        genCode.append("\n")
                .append("),\n");
        return genCode.toString();
    }

}