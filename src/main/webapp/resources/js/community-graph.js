function CommunityGraph() {
	this.svg = null;
	this.force = null;
	
	this.onCreate("body");
}

CommunityGraph.prototype.onCreate = function(elementName) {
	var width = 960, height = 500;
	svg = d3.select(elementName).append("svg")
		.attr("width", width)
		.attr("height", height);
	
	force = d3.layout.force()
		.gravity(.05)
		.distance(100)
		.charge(-100)
		.size([width, height]);
};

CommunityGraph.prototype.load = function(url) {
	d3.json(url, function(error, json) {
  		force.nodes(json.nodes).links(json.links).start();

		var link = this.svg.selectAll(".link")
			.data(json.links)
			.enter().append("line")
			.attr("class", "link");

  		var node = this.svg.selectAll(".node").data(json.nodes)
			.enter().append("g")
			.attr("class", "node")
			.call(force.drag);

		node.append("image")
			.attr("xlink:href", function(d) { 
				if (d.type === 'Circle') {
					return 'resources/img/google_circle.svg';
				}

				return d.imageUrl; 
			})
			.attr("x", -16)
			.attr("y", -16)
			.attr("width", 32)
			.attr("height", 32);
		
		node.append("text")
			.attr("dx", 12)
			.attr("dy", ".35em")
			.text(function(d) { return d.name; });
		
		force.on("tick", function() {
			link.attr("x1", function(d) { return d.source.x; })
				.attr("y1", function(d) { return d.source.y; })
				.attr("x2", function(d) { return d.target.x; })
				.attr("y2", function(d) { return d.target.y; });
			
    		node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
  		});
	});
};