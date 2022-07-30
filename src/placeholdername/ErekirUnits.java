package placeholdername;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.mod.*;
import placeholdername.content.CloakUnits;
import placeholdername.content.EUContent;
import rhino.ImporterTopLevel;
import rhino.NativeJavaPackage;

public class ErekirUnits extends Mod{
    public ErekirUnits(){}

    @Override
    public void loadContent() {
        new EUContent().load();
        new CloakUnits().load();

    }

    //stolen from sh1p
    public static NativeJavaPackage p = null;

    @Override
    public void init() {
        super.init();
        ImporterTopLevel scope = (ImporterTopLevel) Vars.mods.getScripts().scope;

        Seq<String> packages = Seq.with(
                "placeholdername",
                "placeholdername.types",
                "placeholdername.content"
        );

        packages.each(name -> {
            p = new NativeJavaPackage(name, Vars.mods.mainLoader());

            p.setParentScope(scope);

            scope.importPackage(p);
        });
    }
}