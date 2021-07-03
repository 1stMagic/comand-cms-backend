package com.biock.cms.jcr;

import com.biock.cms.exception.RuntimeIOException;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.jcr.exception.RuntimeSAXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.jcr.*;
import javax.jcr.retention.RetentionManager;
import javax.jcr.security.AccessControlManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;

import static java.util.Objects.requireNonNull;

public class CloseableJcrSession implements Session, AutoCloseable{

    private final Session session;

    public CloseableJcrSession(final Session session) {

        requireNonNull(session, "JCR session is null");
        this.session = session;
    }

    public static CloseableJcrSession adminSession(final Repository repository) {

        try {
            return new CloseableJcrSession(repository.login(new SimpleCredentials("admin", "admin".toCharArray())));
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Repository getRepository() {

        return this.session.getRepository();
    }

    @Override
    public String getUserID() {

        return this.session.getUserID();
    }

    @Override
    public String[] getAttributeNames() {

        return this.session.getAttributeNames();
    }

    @Override
    public Object getAttribute(final String name) {

        return this.session.getAttribute(name);
    }

    @Override
    public Workspace getWorkspace() {

        return this.session.getWorkspace();
    }

    @Override
    public Node getRootNode() {

        try {
            return this.session.getRootNode();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Session impersonate(final Credentials credentials) {

        try {
            return this.session.impersonate(credentials);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Node getNodeByUUID(final String id) {

        try {
            return this.session.getNodeByIdentifier(id);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Node getNodeByIdentifier(final String id) {

        try {
            return this.session.getNodeByIdentifier(id);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Item getItem(final String absPath) {

        try {
            return this.session.getItem(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Node getNode(final String absPath) {

        try {
            return this.session.getNode(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public Property getProperty(final String absPath) {

        try {
            return this.session.getProperty(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean itemExists(final String absPath) {

        try {
            return this.session.itemExists(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean nodeExists(final String absPath) {

        try {
            return this.session.nodeExists(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean propertyExists(final String absPath) {

        try {
            return this.session.propertyExists(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void move(final String srcAbsPath, final String destAbsPath) {

        try {
            this.session.move(srcAbsPath, destAbsPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void removeItem(final String absPath) {

        try {
            this.session.removeItem(absPath);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void save() {

        try {
            this.session.save();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void refresh(final boolean keepChanges) {

        try {
            this.session.refresh(keepChanges);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean hasPendingChanges() {

        try {
            return this.session.hasPendingChanges();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public ValueFactory getValueFactory() {

        try {
            return this.session.getValueFactory();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean hasPermission(final String absPath, final String actions) {

        try {
            return this.session.hasPermission(absPath, actions);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void checkPermission(final String absPath, final String actions) throws AccessControlException {

        try {
            this.session.checkPermission(absPath, actions);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public boolean hasCapability(final String methodName, final Object target, final Object[] arguments) {

        try {
            return this.session.hasCapability(methodName, target, arguments);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public ContentHandler getImportContentHandler(final String parentAbsPath, final int uuidBehavior) {

        try {
            return this.session.getImportContentHandler(parentAbsPath, uuidBehavior);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void importXML(final String parentAbsPath, final InputStream in, final int uuidBehavior) {

        try {
            this.session.importXML(parentAbsPath, in, uuidBehavior);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void exportSystemView(final String absPath, final ContentHandler contentHandler, final boolean skipBinary, final boolean noRecurse) {

        try {
            this.session.exportSystemView(absPath, contentHandler, skipBinary, noRecurse);
        } catch (final SAXException e) {
            throw new RuntimeSAXException(e);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void exportSystemView(final String absPath, final OutputStream out, final boolean skipBinary, final boolean noRecurse) {

        try {
            this.session.exportSystemView(absPath, out, skipBinary, noRecurse);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void exportDocumentView(final String absPath, final ContentHandler contentHandler, final boolean skipBinary, final boolean noRecurse) {

        try {
            this.session.exportDocumentView(absPath, contentHandler, skipBinary, noRecurse);
        } catch (final SAXException e) {
            throw new RuntimeSAXException(e);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void exportDocumentView(final String absPath, final OutputStream out, final boolean skipBinary, final boolean noRecurse) {

        try {
            this.session.exportDocumentView(absPath, out, skipBinary, noRecurse);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void setNamespacePrefix(final String prefix, final String uri) {

        try {
            this.session.setNamespacePrefix(prefix, uri);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public String[] getNamespacePrefixes() {

        try {
            return this.session.getNamespacePrefixes();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public String getNamespaceURI(final String prefix) {

        try {
            return this.session.getNamespaceURI(prefix);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public String getNamespacePrefix(final String uri) {

        try {
            return this.session.getNamespacePrefix(uri);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void logout() {

        this.session.logout();
    }

    @Override
    public boolean isLive() {

        return this.session.isLive();
    }

    @Override
    public void addLockToken(final String lt) {

        try {
            this.session.getWorkspace().getLockManager().addLockToken(lt);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public String[] getLockTokens() {

        try {
            return this.session.getWorkspace().getLockManager().getLockTokens();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void removeLockToken(final String lt) {

        try {
            this.session.getWorkspace().getLockManager().removeLockToken(lt);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public AccessControlManager getAccessControlManager() {

        try {
            return this.session.getAccessControlManager();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public RetentionManager getRetentionManager() {

        try {
            return this.session.getRetentionManager();
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public boolean hasNode(final String absPath) {

        try {
            return this.session.getNode(absPath) != null;
        } catch (final PathNotFoundException e) {
            return false;
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    @Override
    public void close() {

        if (this.session != null) {
            this.session.logout();
        }
    }
}
