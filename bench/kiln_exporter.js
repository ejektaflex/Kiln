(function() {
var type;

var kilnModelCodec = new Codec('kiln_exportable_model', {
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
				baseModel.boxes.push(makeBox(obj));
			} else {
				entity.models.push(makeModel(obj, null))
			}
		});

		entity.models.push(baseModel);

		entity.models.forEach(model => {
			prepareModel(model);
		});

		return autoStringify(entity);
    }
})

var kilnAnimCodec = new Codec('kiln_exportable_anim', {
    name: 'Kiln Animation',
    extension: 'anim.json',
    remember: false,
    compile(options) {
        var data = {
			animations: []
		};
		
		Animator.animations.forEach(anim => {
			data.animations.push(makeAnim(anim))
		});

		return autoStringify(data);
    }
})


function makeEmptyAnim() {
	return {
		name: "",
		length: 0,
		bones: []
	}
}

function makeEmptyBone() {
	return {
		rot: [],
		pos: []
	}
}


function makeAnim(obj) {
	var anim = makeEmptyAnim();

	anim.name = obj.name;
	anim.length = obj.length;

	function compareFrame(a, b) {
		return parseFloat(a.time) - parseFloat(b.time);
	}

	Object.values(obj.animators).forEach(boneMover => {


		if (boneMover.rotation.length > 0 || boneMover.position.length > 0) {

			var bone = makeEmptyBone();

			bone.name = boneMover.name;

			boneMover.rotation.sort(compareFrame).forEach(frame => {
				console.log(frame.time);
				bone.rot.push([parseFloat(frame.time), parseFloat(frame.x), parseFloat(frame.y), parseFloat(frame.z)]);
			});
			boneMover.position.sort(compareFrame).forEach(frame => {
				bone.pos.push([parseFloat(frame.time), parseFloat(frame.x) / 16, parseFloat(frame.y) / 16, parseFloat(frame.z) / 16]);
			});
	
			anim.bones.push(bone);
		}

		
	});

	return anim;
}


// Offset box positions based on model pivots
function prepareModel(model, pivotSum = [0, 0, 0]) {
	var newPivotSum = [
		model.pivot[0] + pivotSum[0],
		model.pivot[1] + pivotSum[1],
		model.pivot[2] + pivotSum[2],
	];

	model.boxes.forEach(box => {
		box.pos[0] -= newPivotSum[0];
		box.pos[1] -= newPivotSum[1];
		box.pos[2] -= newPivotSum[2];
	})

	model.submodels.forEach(submodel => {
		prepareModel(submodel, newPivotSum);
	});
}

function makeEmptyBox() {
	return {
		name: "",
		uv: [0, 0],
		pos: [0.0, 0.0, 0.0],
		size: [0.0, 0.0, 0.0]
	}
}

function makeEmptyModel() {
	return {
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
			model.boxes.push(makeBox(child));
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

function makeBox(obj) {
	var cube = makeEmptyBox();
	cube.name = obj.name;
	cube.uv[0] = Math.floor(obj.uv_offset[0]);
	cube.uv[1] = Math.floor(obj.uv_offset[1]);
	cube.pos[0] = -obj.to[0];
	cube.pos[1] = -obj.to[1];
	cube.pos[2] = obj.from[2];
	
	cube.size[0] = obj.size(0, true);
	cube.size[1] = obj.size(1, true);
	cube.size[2] = obj.size(2, true);
	return cube;
}

var onExportModel;
var onExportAnim;

Plugin.register('kiln_exporter', {
	title: 'Kiln Exporter',
	author: 'Ejektaflex',
	icon: 'looks_2',
	description: 'Allows your models to be exported in a format that Kiln can read!',
	version: '1.0.0',
	variant: 'both',
	min_version: '1.0.0',
	onload() {
		onExportModel = new Action({
			id: 'export_to_kiln_model',
			name: 'Export to Kiln Entity Model',
			icon: 'archive',
			description: 'Allows your models to be exported in a format that Kiln can read!',
			category: 'file',
			condition: () => Format.id === 'bedrock',
			click: function () {
				kilnModelCodec.export();
			}
		});

		onExportAnim = new Action({
			id: 'export_to_kiln_anim',
			name: 'Export Kiln Animation',
			icon: 'archive',
			description: 'Allows your animation to be exported in a format that Kiln can read!',
			category: 'animation',
			condition: () => Format.id === 'bedrock',
			click: function () {
				kilnAnimCodec.export();
			}
		});

		MenuBar.addAction(onExportModel, 'file.export');
		MenuBar.addAction(onExportAnim, 'animation');

		
	},
	onunload() {
		onExportModel.delete();
		onExportAnim.delete();

		console.clear();
	}
});


})()