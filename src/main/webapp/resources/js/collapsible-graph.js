function CollapsibleGraph() {
	this.svg = null;
	this.force = null;
	this.link = null;
	this.node = null;
	this.root = null;
	this.width = 1000;
	this.height = 800;

	this.onCreate("body");
}

CollapsibleGraph.prototype.onCreate = function (elementName) {
	force = d3.layout.force()
		.size([this.width, this.height])
		.on("tick", this.tick);

	svg = d3.select(elementName).append("svg")
		.attr("width", this.width)
		.attr("height", this.height);

	link = svg.selectAll(".link");
	node = svg.selectAll(".node");

};

CollapsibleGraph.prototype.load = function (url) {
	$this = this;
	d3.json(url, function (json) {
		root = json;
		update();
	});

	function update() {
		var nodes = flatten(root),
			links = d3.layout.tree().links(nodes);

		// Restart the force layout.
		force
			.nodes(nodes)
			.links(links)
			.start();

		// Update the links…
		link = link.data(links, function (d) {
			return d.target.id;
		});

		// Exit any old links.
		link.exit().remove();

		// Enter any new links.
		link.enter().insert("line", ".node")
			.attr("class", "link")
			.attr("x1", function (d) {
				return d.source.x;
			})
			.attr("y1", function (d) {
				return d.source.y;
			})
			.attr("x2", function (d) {
				return d.target.x;
			})
			.attr("y2", function (d) {
				return d.target.y;
			});

		// Update the nodes…
		node = node.data(nodes, function (d) {
			return d.id;
		});

		node.exit().remove();

		var enterNode = node.enter();

		var gNode = enterNode.append("g");
		gNode.attr("class", "node").on("click", click).call(force.drag);

		var imageUtil = new ImageUtil();
		gNode.append("image")
			.attr("xlink:href", function (d) {
				if (d.type === 'circle') {
					return 'resources/img/google_circle.svg';
				}

				return d.imageHref;
			})
			.attr("x", function (d) {
				return imageUtil.offset(d);
			})
			.attr("y", function (d) {
				return imageUtil.offset(d);
			})
			.attr("width", function (d) {
				return imageUtil.size(d);
			})
			.attr("height", function (d) {
				return imageUtil.size(d);
			});

		gNode.append("text")
			.attr("dx", 12)
			.attr("dy", ".35em")
			.text(function (d) {
				return d.label;
			});
	}

// Color leaf nodes orange, and packages white or blue.
	function color(d) {
		return d._children ? "#3182bd" : d.children ? "#c6dbef" : "#fd8d3c";
	}

// Toggle children on click.
	function click(d) {
		if (!d3.event.defaultPrevented) {
			if (d.children) {
				d._children = d.children;
				d.children = null;
			} else {
				d.children = d._children;
				d._children = null;
			}
			update();
		}
	}

// Returns a list of all nodes under the root.
	function flatten(root) {
		var nodes = [], i = 0;

		function recurse(node) {
			if (node.children) node.children.forEach(recurse);
			if (!node.id) node.id = ++i;
			nodes.push(node);
		}

		recurse(root);
		return nodes;
	}
}

CollapsibleGraph.prototype.tick = function tick() {
	link.attr("x1", function (d) {
		return d.source.x;
	})
		.attr("y1", function (d) {
			return d.source.y;
		})
		.attr("x2", function (d) {
			return d.target.x;
		})
		.attr("y2", function (d) {
			return d.target.y;
		});

	node.attr("transform", function (d) {
		return "translate(" + d.x + "," + d.y + ")";
	});
}

function ImageUtil() {
	this.defSize = 8;
}

ImageUtil.prototype.size = function (node) {
	if (node.root)
		return this.defSize * 3;
	if (node.type === 'circle') {
		return this.defSize;
	} else {
		return this.defSize * 2;
	}
};

ImageUtil.prototype.offset = function (node) {
	return this.size(node) / -2;
};