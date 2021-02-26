package frc.lib.autonomous;

public class Path {
    protected Plot[] m_plots;
    private int m_current = 0;
    public double m_TranslationTolerance;
    public double m_RotationalTolerance;

    public Path() {}

    public Path(Plot[] path) {
        this.m_plots = path;
    }

    public Plot getCurrent() {
        return this.m_plots[this.m_current];
    }

    public void reset() {
        this.m_current = 0;
    }

    public Plot getAhead() {
        if (this.m_current+1 != this.m_plots.length) {
            return this.m_plots[this.m_current+1];
        } else {
            return this.getCurrent();
        }
    }

    public void up() {
        if (next()) {
            this.m_current++;
        }
    }

    public boolean next() {
        return this.m_current+1 != this.m_plots.length;
    }
}
