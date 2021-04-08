package net.atesu.atesvcdataapi.model.VO;

/**
 * json请求参数
 */
public class DemoForm {
    private String name;
    private DemoVO demoVo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DemoVO getDemoVo() {
        return demoVo;
    }

    public void setDemoVo(DemoVO demoVo) {
        this.demoVo = demoVo;
    }
}
