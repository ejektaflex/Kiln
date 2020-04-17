(function() {
var type;

var kilnCodec = new Codec('kiln_exportable', {
    name: 'Kiln Model',
    extension: 'json',
    remember: false,
    compile(options) {
        var entity = {
			scale: 1.0,
			texture_width: Project.texture_width,
			texture_height: Project.texture_height,
			models: []
		};
		
		let baseModel = makeEmptyModel();
		baseModel.name = "loose_boxes";

		Outliner.root.forEach(obj => {
			console.log("It's a " + obj.type);
			if (obj.type === 'cube') {
				baseModel.boxes.push(makeBox(obj, baseModel));
			} else {
				entity.models.push(makeModel(obj, null))
				//let cube = recurvBBGroup(obj, createCubeFromGroup(obj, true));
				//model.groups.push(cube);
			}
		});

		entity.models.push(baseModel);

		return autoStringify(entity);
    }
})

function makeEmptyBox() {
	return {
		//type: "box",
		name: "",
		uv: [0, 0],
		pos: [0.0, 0.0, 0.0],
		size: [0.0, 0.0, 0.0]
	}
}

function makeEmptyModel() {
	return {
		//type: "model",
		name: "",
		pivot: [0.0, 0.0, 0.0],
		angle: [0.0, 0.0, 0.0],
		boxes: [],
		submodels: []
	}
}

function makeModel(obj, parent) {
	var model = makeEmptyModel();

	model.pivot = [-obj.origin[0], -obj.origin[1], obj.origin[2]]

	
	model.name = obj.name;
	model.angle = [
		-obj.rotation[0],
		-obj.rotation[1],
		obj.rotation[2]
	]

	// recursion
	obj.children.forEach(child => {
		if (child.type == "cube") {
			model.boxes.push(makeBox(child, parent));
		} else {
			model.submodels.push(makeModel(child, model));
		}
	});

	if (parent != null) {
		model.pivot[0] -= parent.pivot[0];
		model.pivot[1] -= parent.pivot[1];
		model.pivot[2] -= parent.pivot[2];
	}
	
	return model;
}

function makeBox(obj, parent) {
	var cube = makeEmptyBox();
	cube.name = obj.name;
	cube.uv[0] = Math.floor(obj.uv_offset[0]);
	cube.uv[1] = Math.floor(obj.uv_offset[1]);
	cube.pos[0] = -obj.to[0];
	cube.pos[1] = -obj.to[1];
	cube.pos[2] = obj.from[2];
	
	if (parent != null) {
		cube.pos[0] -= parent.pivot[0];
		cube.pos[1] -= parent.pivot[1];
		cube.pos[2] -= parent.pivot[2];
	}
	
	cube.size[0] = obj.size(0, true);
	cube.size[1] = obj.size(1, true);
	cube.size[2] = obj.size(2, true);
	return cube;
}

var onExport;

Plugin.register('kiln_exporter', {
	title: 'Kiln Exporter',
	author: 'Ejektaflex',
	icon: 'looks_2',
	description: 'Allows your models to be exported in a format that Kiln can read!',
	version: '1.0.0',
	variant: 'both',
	min_version: '1.0.0',
	onload() {
		onExport = new Action({
			id: 'export_to_kiln',
			name: 'Export to Kiln Entity Model',
			icon: 'archive',
			description: 'Allows your models to be exported in a format that Kiln can read!',
			category: 'file',
			condition: () => Format.id === 'bedrock',
			click: function () {
				kilnCodec.export();
			}
		});

		MenuBar.addAction(onExport, 'file.export');
	},
	onunload() {
		onExport.delete();
		console.clear();
	}
});


})()