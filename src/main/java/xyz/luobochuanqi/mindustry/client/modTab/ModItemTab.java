package xyz.luobochuanqi.mindustry.client.modTab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import xyz.luobochuanqi.mindustry.init.ItemRegister;

public class ModItemTab extends ItemGroup {
    public ModItemTab() {
        super("mod_item_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegister.obsidianBlock.get());
    }
}