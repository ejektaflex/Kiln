// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

public class crawler extends EntityModel {
	private final RendererModel bb_main;
	private final RendererModel left_arm;

	public crawler() {
		textureWidth = 64;
		textureHeight = 64;

		bb_main = new RendererModel(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -12.0F, -8.0F, 8, 8, 8, 0.0F, false));

		left_arm = new RendererModel(this);
		left_arm.setRotationPoint(0.0F, 24.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
		left_arm.render(f5);
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}