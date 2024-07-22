package dev.hexnowloading.dungeonnowloading.particle.type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class AxisParticleType extends ParticleType<AxisParticleType.AxisParticleData> {

    public static final Codec<AxisParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("particle_type").forGetter(data -> BuiltInRegistries.PARTICLE_TYPE.getKey(data.particleType).toString()),
            Codec.INT.fieldOf("axis").forGetter(data -> data.axis),
            Codec.FLOAT.fieldOf("degree").forGetter(data -> data.degree)
    ).apply(instance, (type, axis, degree) -> new AxisParticleData((ParticleType<AxisParticleData>)BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(type)), axis, degree)));

    public AxisParticleType(boolean alwaysShow) {
        super(alwaysShow, AxisParticleData.DESERIALIZER);
    }

    @Override
    public Codec<AxisParticleData> codec() {
        return CODEC;
    }

    public static class AxisParticleData implements ParticleOptions {

        public static final Deserializer<AxisParticleData> DESERIALIZER = new Deserializer<>() {

            public AxisParticleData fromCommand(ParticleType<AxisParticleData> particleType, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                int axis = reader.readInt();
                reader.expect(' ');
                float degree = reader.readFloat();

                return new AxisParticleData(particleType, axis, degree);
            }

            public AxisParticleData fromNetwork(ParticleType<AxisParticleData> particleType, FriendlyByteBuf buffer) {
                return new AxisParticleData(particleType, buffer.readInt(), buffer.readFloat());
            }
        };

        private final ParticleType<AxisParticleData> particleType;
        private final int axis;
        private final float degree;

        public AxisParticleData(ParticleType<AxisParticleData> particleType, int axis, float degree) {
            this.particleType = particleType;
            this.axis = axis;
            this.degree = degree;
        }

        @Override
        public String writeToString() {
            return String.format(Locale.ROOT, "%s", BuiltInRegistries.PARTICLE_TYPE.getKey(getType()));
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeInt(axis);
            buffer.writeFloat(degree);
        }

        @Override
        public ParticleType<?> getType() {
            return particleType;
        }

        public int getAxis() {
            return axis;
        }

        public float getDegree() {
            return degree;
        }
    }
}
