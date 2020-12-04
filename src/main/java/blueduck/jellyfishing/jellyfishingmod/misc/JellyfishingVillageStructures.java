package blueduck.jellyfishing.jellyfishingmod.misc;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.ProcessorLists;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JellyfishingVillageStructures {

        public static void init() {
                PlainsVillagePools.init();

                addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("jellyfishing:village/krusty_krab_plains"), 1);
                addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("jellyfishing:village/scrap_metal_forge"), 4);

            }

        private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
            JigsawPattern old = WorldGenRegistries.JIGSAW_POOL.getOrDefault(pool);
            List<JigsawPiece> shuffled = old != null ? old.getShuffledPieces(new Random()) : ImmutableList.of();
            List<Pair<JigsawPiece, Integer>> newPieces = shuffled.stream().map(p -> new Pair<>(p, 1)).collect(Collectors.toList());
            newPieces.add(new Pair<>(new LegacySingleJigsawPiece(Either.left(toAdd), () -> ProcessorLists.field_244101_a, JigsawPattern.PlacementBehaviour.RIGID), weight));
            ResourceLocation name = old.getName();
            Registry.register(WorldGenRegistries.JIGSAW_POOL, pool, new JigsawPattern(pool, name, newPieces));
        }

}
