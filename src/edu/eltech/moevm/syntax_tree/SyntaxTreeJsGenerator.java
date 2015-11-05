package edu.eltech.moevm.syntax_tree;

import java.util.ArrayList;

/**
 * Created by lazorg on 11/3/15.
 */
public class SyntaxTreeJsGenerator implements TreeCallback {
    public ArrayList<String> nodes;
    public ArrayList<String> branches;

    public SyntaxTreeJsGenerator() {
        nodes = new ArrayList<String>();
        branches = new ArrayList<String>();
    }

    @Override
    public void processElement(TreeElement e, int level) {

        if (e != null)
            if (e instanceof Leaf) {
                Leaf cur = ((Leaf) e);
                String name = ((Leaf) e).getOperand().name();
                int hc = cur.getId();
                if (cur.getValue() != null)
                    name += " (" + cur.getValue() + ")";
                if (cur.getType() != null)
                    name += " <" + cur.getType() + ">";
                nodes.add(new String("g.setNode(" + hc + ",  { label: \"" + name + "\",\t class: \"type-TK\"});"));
            } else if (e instanceof Node) {
                Node cur = ((Node) e);
                String name = cur.getOperation().name();
                int hc = cur.getId();

                if (cur.getValue() != null)
                    name += " (" + cur.getValue() + ")";
                if (cur.getType() != null)
                    name += " <" + cur.getType() + ">";
                nodes.add(new String("g.setNode(" + hc + ",  { label: \"" + name + "\",\t class: \"type-NT\"});"));
                if (e instanceof Node) {
                    try {
                        for (TreeElement node : e.getElements()) {
                            branches.add(new String("g.setEdge(" + hc + ", " + node.getId() + ",{ lineInterpolate: 'basis' });"));
                        }
                    } catch (edu.eltech.moevm.syntax_tree.UnsupportedOperationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
    }

    public String genTree() {
        String result = new String();

        result += "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>Syntax tree visualisation</title>\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"css/demo.css\">\n" +
                "<script src=\"js/d3.v3.min.js\" charset=\"utf-8\"></script>\n" +
                "<script src=\"js/dagre-d3.min.js\"></script>\n" +
                "\n" +
                "<style id=\"css\">\n" +
                "body {\n" +
                "  margin: 0px;\n" +
                "  padding: 0px;\n" +
                "  overflow: hidden;\n" +
                "}\n" +
                "g.type-TK > rect {\n" +
                "  fill: #00ffd0;\n" +
                "}\n" +
                "\n" +
                "text {\n" +
                "  font-weight: 300;\n" +
                "  font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serf;\n" +
                "  font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".node rect {\n" +
                "  stroke: #999;\n" +
                "  fill: #fff;\n" +
                "  stroke-width: 2.5px;\n" +
                "}\n" +
                "\n" +
                ".edgePath path {\n" +
                "  stroke: #333;\n" +
                "  stroke-width: 1.5px;\n" +
                "}\n" +
                "</style>\n" +
                "</head><body>\n" +
                "<script type=\"text/javascript\">" +
                "    var w = window,  d = document, e = d.documentElement,  g = d.getElementsByTagName('body')[0],  x = w.innerWidth || e.clientWidth || g.clientWidth,  y = w.innerHeight|| e.clientHeight|| g.clientHeight;" +
                "    document.write(\"<svg id=\\\"svg-canvas\\\" width=\\\"\"+x+\"\\\" height=\\\"\"+y+\"\\\"></svg>\");" +
                "</script>\n" +
                "<script type=\"text/javascript\" id=\"js\">\n" +
                "var g = new dagreD3.graphlib.Graph()\n" +
                "  .setGraph({})\n" +
                "  .setDefaultEdgeLabel(function() { return {}; });\n";

        for (String str : nodes)
            result += str + "\n";

        result += "g.nodes().forEach(function(v) {\n" +
                "  var node = g.node(v);\n" +
                "  node.rx = node.ry = 5;\n" +
                "});\n";

        for (String str : branches)
            result += str + "\n";

        result += "var render = new dagreD3.render();\n" +
                "\n" +
                "var svg = d3.select(\"svg\"),\n" +
                "    svgGroup = svg.append(\"g\");\n" +
                "    \n" +
                "render(d3.select(\"svg g\"), g);\n" +
                "var svg2 = d3.select(\"svg\"),\n" +
                "    inner = d3.select(\"svg g\"),\n" +
                "    zoom = d3.behavior.zoom().on(\"zoom\", function() {\n" +
                "      inner.attr(\"transform\", \"translate(\" + d3.event.translate + \")\" +\n" +
                "                                  \"scale(\" + d3.event.scale + \")\");\n" +
                "    });\n" +
                "svg2.call(zoom);\n" +
                "\n" +
                "var xCenterOffset = (svg.attr(\"width\") - g.graph().width) / 2;\n" +
                "svgGroup.attr(\"transform\", \"translate(\" + xCenterOffset + \", 20)\");\n" +
                "</script>\n" +
                "</body></html>\n";


        return result;
    }
}
