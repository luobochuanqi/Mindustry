package xyz.luobochuanqi.mindustry.common.util;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import xyz.luobochuanqi.mindustry.Utils;
import xyz.luobochuanqi.mindustry.common.world.Item.Ores;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Utils.ModID)
public class DataGenHandler {
    @SubscribeEvent
    public static void register(GatherDataEvent event) {
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeClient()) {
            //block/item models, blockstates, language files...
            //create en_us.json
//            event.getGenerator().addProvider(new ModLanguageProvider(event.getGenerator(), Utils.ModID, "en_us"));
            //gen models.json
            event.getGenerator().addProvider(new ModItemModelProvider(event.getGenerator(), Utils.ModID, event.getExistingFileHelper()));
        }
        if (event.includeServer()) {
            //recipes,advancements,tags...
        }
        if (event.includeReports()){
            //world
        }
    }

    public static class ModLanguageProvider extends LanguageProvider {
        public ModLanguageProvider(DataGenerator gen, String modid, String locale) {
            super(gen, modid, locale);
        }

        @Override
        protected void addTranslations() {
            for (Ores.OreType myOre : Ores.OreType.values()) {
                add("item.mindustry." + myOre.toString(), underline2hump(myOre.toString()));
            }
        }

        public String pro2json(String name) throws IOException {
            InputStream inputStream = this.getClass().getResourceAsStream("bundle_zh_CN.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("item." + name + ".name");
        }

        public static String underline2hump(String str) {
            String regex = "_(.)";
            Matcher matcher = Pattern.compile(regex).matcher(str);
            while (matcher.find()) {
                String target = matcher.group(1);
                str = str.replaceAll("_"+target, " " + target.toUpperCase());
            }
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            return str;
        }
    }

    public static class ModItemModelProvider extends ItemModelProvider {
        public final ResourceLocation GENERATED = new ResourceLocation("item/generated");
        public final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

        public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
            super(generator, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            for (Ores.OreType myOre : Ores.OreType.values()) {
                generatedItem(myOre.toString());
            }
        }

        protected final ItemModelBuilder generatedItem(String name) {
            return withExistingParent(name, GENERATED)
                    .texture("layer0", modLoc("item/" + name));
        }
    }
}
