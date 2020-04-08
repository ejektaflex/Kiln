(function() {
var type;

var kilnCodec = new Codec('kiln_exportable', {
    name: 'Kiln Model',
    extension: 'json',
    remember: false,
    compile(options) {
        var model = {
			type: type,
			scale: 1.0,
			texture_width: Project.texture_width,
			texture_height: Project.texture_height
        };
        
        var cubes = [];
		Outliner.root.forEach(obj => {
			if (obj.type === 'cube') {
				cubes.push(createCube(obj));
			} else {
				var cube = recurvBBGroup(obj, createCubeFromGroup(obj, true));
				cubes.push(cube);
			}
		});
		
		model.cubes = cubes;
		return autoStringify(model);
    }
})

function makeCube() {
	return {
		name: "",
		scale: 1.0,
		mirror: false,
		tex_off: [0, 0],
		off: [0.0, 0.0, 0.0],
		rot_point: [0.0, 0.0, 0.0],
		size: [0, 0, 0],
		rot: [0.0, 0.0, 0.0]
	}
}

function createCube(obj) {	
	var cube = makeCube();
	cube.name = obj.name;
	cube.scale = obj.inflate + 1;
	cube.mirror = obj.mirror_uv;
	cube.tex_off[0] = Math.floor(obj.uv_offset[0]);
	cube.tex_off[1] = Math.floor(obj.uv_offset[1]);
	cube.off[0] = -obj.to[0];
	cube.off[1] = 24-obj.to[1];
	cube.off[2] = obj.from[2];
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
			condition: () => Format.id === 'modded_entity',
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




function createCubeFromGroup(group, isRoot) {
	var cube = makeCube();
	
	cube.name = group.name;
	cube.rot_point[0] = -group.origin[0];
	cube.rot_point[1] = -group.origin[1] + (isRoot ? 24 : 0);
	cube.rot_point[2] = group.origin[2];
	cube.rot[0] = -group.rotation[0];
	cube.rot[1] = -group.rotation[1];
	cube.rot[2] = group.rotation[2];
	
	return cube;
}

function combineCubeIntoGroup(groupObj, groupCube, cube, cubeObj) {
	groupCube.scale = cube.scale;
	groupCube.mirror = cube.mirror;
	groupCube.tex_off = cube.tex_off;
	groupCube.size = cube.size;
	cube.off[0] += groupObj.origin[0];
	cube.off[1] = (-cubeObj.from[1] - cubeObj.size(1, true) + groupObj.origin[1]);
	cube.off[2] -= groupCube.rot_point[2];
	groupCube.off = cube.offset;
	
	return groupCube;
}

function parentCubeToGroup(groupCube, cube) {
	cube.off[0] -= groupCube.rot_point[0];
	cube.off[1] -= groupCube.rot_point[1];
	cube.off[2] -= groupCube.rot_point[2];
	
	return cube;
}

function parentGroup2ToGroup1(parentObj, group) {
	group.rot_point[0] += parentObj.origin[0];
	group.rot_point[1] += parentObj.origin[1];
	group.rot_point[2] -= parentObj.origin[2];
	return group;
}

// obj and cube are always of groups
function recurvBBGroup(obj, cube) {
	if (obj.children.length > 0) {
		obj.children.forEach(childObj => {
			if (childObj.type !== 'cube') {
				var childGroupCube = parentGroup2ToGroup1(obj, createCubeFromGroup(childObj));
				childGroupCube = recurvBBGroup(childObj, childGroupCube);
					
				if (cube.children == null) {
                    cube.children = []
                }
				
				cube.children.push(childGroupCube);
			} else {
				var childCube = createCube(childObj);
				
				if (childCube.name === cube.name) {
					cube = combineCubeIntoGroup(obj, cube, childCube, childObj);
				} else {
					if (cube.children == null) {
                        cube.children = []
                    }
					childCube = parentCubeToGroup(cube, childCube);
					cube.children.push(childCube);
				}
			}
		});
	}
	return cube;
}


})()