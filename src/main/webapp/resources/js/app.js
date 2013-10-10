function AppLogic() {
	this.cg = new CommunityGraph();
}

AppLogic.prototype.load = function(url) {
	return this.cg.load(url);
};